package com.tiket.io.sqlite;

import com.tiket.io.FailureDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.Duration;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * SQLite-backed implementation of {@link FailureDB}.
 *
 * Schema:
 * <pre>
 * failures(
 *   id INTEGER PRIMARY KEY AUTOINCREMENT,
 *   ts_ms INTEGER NOT NULL,
 *   link TEXT,
 *   api TEXT,
 *   module TEXT,
 *   vertical TEXT
 * )
 * </pre>
 */
public class SQLiteFailureDB implements FailureDB {

    private static final Logger logger = LogManager.getLogger(SQLiteFailureDB.class);

    private final String jdbcUrl;
    private final boolean keepSingleConnection;
    private Connection singleConnection;

    public SQLiteFailureDB(Path dbFilePath) {
        Objects.requireNonNull(dbFilePath, "dbFilePath");
        try {
            Path parent = dbFilePath.toAbsolutePath().getParent();
            if (parent != null) Files.createDirectories(parent);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create directory for DB file: " + dbFilePath, e);
        }
        this.jdbcUrl = "jdbc:sqlite:" + dbFilePath.toAbsolutePath();
        this.keepSingleConnection = false;
        init();
    }

    /** In-memory DB (useful for tests). */
    public static SQLiteFailureDB inMemory() {
        return new SQLiteFailureDB("jdbc:sqlite::memory:", true);
    }

    private SQLiteFailureDB(String jdbcUrl, boolean keepSingleConnection) {
        this.jdbcUrl = Objects.requireNonNull(jdbcUrl, "jdbcUrl");
        this.keepSingleConnection = keepSingleConnection;
        init();
    }

    private synchronized Connection getConnection() throws SQLException {
        if (keepSingleConnection) {
            if (singleConnection == null || singleConnection.isClosed()) {
                singleConnection = DriverManager.getConnection(jdbcUrl);
                applyPragmas(singleConnection);
            }
            return singleConnection;
        }

        Connection conn = DriverManager.getConnection(jdbcUrl);
        applyPragmas(conn);
        return conn;
    }

    private void applyPragmas(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA foreign_keys = ON");
            st.execute("PRAGMA journal_mode = WAL");
            st.execute("PRAGMA synchronous = NORMAL");
            st.execute("PRAGMA busy_timeout = 5000");
        }
    }

    private void init() {
        Connection conn = null;
        Statement st = null;
        try {
            conn = getConnection();
            st = conn.createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS failures (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ts_ms INTEGER NOT NULL," +
                    "link TEXT," +
                    "api TEXT," +
                    "module TEXT," +
                    "vertical TEXT" +
                    ")");
            st.execute("CREATE INDEX IF NOT EXISTS idx_failures_ts ON failures(ts_ms)");
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to initialize SQLiteFailureDB", e);
        } finally {
            if (st != null) {
                try { st.close(); } catch (Exception ignored) {}
            }
            if (!keepSingleConnection) closeQuietly(conn);
        }
    }

    @Override
    public boolean insertFailure(DBFailure dbFailure) {
        Objects.requireNonNull(dbFailure, "dbFailure");
        Failure f = Objects.requireNonNull(dbFailure.failure(), "dbFailure.failure");

        String sql = "INSERT INTO failures (ts_ms, link, api, module, vertical) VALUES (?,?,?,?,?)";

        Connection conn = null;
        try {
            conn = getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, dbFailure.timestamp());
                ps.setString(2, f.link());
                ps.setString(3, f.api());
                ps.setString(4, f.module());
                ps.setString(5, f.vertical());
                int updated = ps.executeUpdate();
                return updated == 1;
            }
        } catch (SQLException e) {
            logger.warn("Failed to insert failure into DB", e);
            return false;
        } finally {
            if (!keepSingleConnection) closeQuietly(conn);
        }
    }

    @Override
    public Set<Failure> getFailures(Duration duration) {
        Objects.requireNonNull(duration, "duration");
        long cutoff = System.currentTimeMillis() - duration.toMillis();

        String sql = "SELECT link, api, module, vertical FROM failures WHERE ts_ms >= ?";
        Set<Failure> out = new HashSet<>();

        Connection conn = null;
        try {
            conn = getConnection();
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, cutoff);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        out.add(new Failure(
                                rs.getString("link"),
                                rs.getString("api"),
                                rs.getString("module"),
                                rs.getString("vertical"),
                                rs.getString("platform")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            logger.warn("Failed to read failures from DB", e);
        } finally {
            if (!keepSingleConnection) closeQuietly(conn);
        }

        return out;
    }

    private static void closeQuietly(Connection conn) {
        if (conn == null) return;
        try {
            conn.close();
        } catch (Exception ignored) {
        }
    }
}
