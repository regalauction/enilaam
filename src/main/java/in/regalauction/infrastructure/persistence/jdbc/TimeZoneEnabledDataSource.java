package in.regalauction.infrastructure.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class TimeZoneEnabledDataSource extends DriverManagerDataSource {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TimeZoneEnabledDataSource.class);
	
	private String timeZone;
	
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	@Override
	public Connection getConnection() throws SQLException {
		
		if (!timeZone.isEmpty()) {
			
			LOGGER.trace("Setting timezone for the connection...");
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			
			try {
				connection = super.getConnection();
				preparedStatement = connection.prepareStatement("SET time_zone = ?");
				preparedStatement.setString(1, timeZone);
				LOGGER.trace(preparedStatement.toString());
				preparedStatement.execute();
			} catch (SQLException e) {
				LOGGER.error("Error while Setting timezone: {}", e);
				e.printStackTrace();
			} finally {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					LOGGER.warn("Error while closing statement: {}", e);
					e.printStackTrace();
				}
			}
			
			return connection;
			
		} else {
			LOGGER.trace("Not setting timezone");
			return super.getConnection();
		}
		
	}
}
