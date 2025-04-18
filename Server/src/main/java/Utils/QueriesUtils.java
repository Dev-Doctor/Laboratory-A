package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class QueriesUtils {
    public static PreparedStatement prepareStatement(Connection conn, String sql, ArrayList<Object> params) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(sql);

        for(int i = 0; i<params.size(); i++) {
            Object param = params.get(i);
            if(param instanceof String) {
                stmt.setString(i + 1, (String) param);
            }
            else if(param instanceof Integer) {
                stmt.setInt(i + 1, (Integer) param);
            }
            else if(param instanceof Float) {
                stmt.setFloat(i + 1, (Float) param);
            }
        }
        return stmt;
    }
}
