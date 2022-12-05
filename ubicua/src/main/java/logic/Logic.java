package logic;

import java.util.ArrayList;

import database.Cliente;
import database.Recogida_autenticar;
import database.Pedido;
import database.Repartidor;
import database.Taquilla;
import database.Taquillero;
import database.ConectionDDBB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logic 
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 
	 * @return The list of all the stations stored in the db
	 */
        
	public static ArrayList<Taquillero> getTaquilleroFromDB()
	{
		ArrayList<Taquillero> taquilleros = new ArrayList<Taquillero>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetTaquilleros(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Taquillero taquillero = new Taquillero();
				taquillero.setId_taquillero(rs.getInt("ID_TAQUILLERO"));
				taquillero.setPais(rs.getString("PAIS"));
                                taquillero.setCiudad(rs.getString("CUIDAD"));
				taquillero.setCodigo_postal(rs.getInt("CODIGO_POSTAL"));
				taquillero.setCalle(rs.getString("CALLE"));
				taquillero.setCodigo_postal(rs.getInt("NUMERO"));
				taquilleros.add(taquillero);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			taquilleros = new ArrayList<Taquillero>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			taquilleros = new ArrayList<Taquillero>();

		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			taquilleros = new ArrayList<Taquillero>();

		} finally
		{
			conector.closeConnection(con);
		}
		return taquilleros;
	}
	
	/**
	 * 
	 * @return The list of all the stations stored in the db of a city
	 */
	public static ArrayList<Taquilla> getTaquillasFromTaquillero(int id_taquillero)
	{
		ArrayList<Taquilla> taquillas = new ArrayList<Taquilla>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationsFromCity(con);
			ps.setInt(1, id_taquillero);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				Taquilla taquilla = new Taquilla();
				taquilla.setId_taquilla(rs.getInt("ID_TAQUILLA"));
				taquilla.setId_taquillero(rs.getInt("ID_TAQUILLERO"));
				taquilla.setOcupado(rs.getBoolean("OCUPADO"));
				taquillas.add(taquilla);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			taquillas = new ArrayList<Taquilla>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			taquillas = new ArrayList<Taquilla>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			taquillas = new ArrayList<Taquilla>();
		} finally
		{
			conector.closeConnection(con);
		}
		return taquillas;
	}
	
	/**
	 * 
	 * @return The list of all the cities stored in the db
	 */
	public static ArrayList<City> getCitiesFromDB()
	{
		ArrayList<City> cities = new ArrayList<City>();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetCities(con);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				City city = new City();
				city.setId(rs.getInt("ID"));
				city.setName(rs.getString("NAME"));
				cities.add(city);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			cities = new ArrayList<City>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			cities = new ArrayList<City>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			cities = new ArrayList<City>();
		} finally
		{
			conector.closeConnection(con);
		}
		return cities;
	}
	
	/**
	 * 
	 * @param idStation ID of the station to search
	 * @return The list of sensors of a Installation
	 */
	public static ArrayList<SensorType> getStationSensorsFromDB(int idStation)
	{
		ArrayList<SensorType> sensors = new ArrayList<SensorType>();	
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensors(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				SensorType sensor = new SensorType();
				sensor.setId(rs.getInt("ID"));
				sensor.setName(rs.getString("NAME"));
				sensor.setUnits(rs.getString("UNITS"));
				if(rs.getInt("STATION_ID")>0)
				{
					sensor.setAvailable(1);
					//EN:Search the last value of the station
					PreparedStatement ps_value = ConectionDDBB.GetLastValueStationSensor(con);
					ps_value.setInt(1, idStation);
					ps_value.setInt(2, rs.getInt("ID"));
					Log.log.info("Query=> {}", ps_value.toString());
					ResultSet rs_value = ps_value.executeQuery();
					if (rs_value.next())
					{
						sensor.setValue(rs_value.getInt("VALUE"));
						if(sensor.getValue()<rs.getInt("MINVALUE"))
						{
							sensor.setLabel(0); //Low value
						}else
						{
							if(sensor.getValue()>rs.getInt("MAXIVALUE"))
							{
								sensor.setLabel(2); //High value
							}else
							{
								sensor.setLabel(1);	 //Medium value
							}
						}
					}
				}else
				{
					sensor.setAvailable(0);
					//TODO buscar la media de la ciudad para dar un valor aproximado
				}
				sensors.add(sensor);
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			sensors = new ArrayList<SensorType>();
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			sensors = new ArrayList<SensorType>();
		} finally
		{
			conector.closeConnection(con);
		}
		return sensors;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getLastWeekStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementLastDays(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			ps.setInt(3, 7); //ES:Number of Days to search //ES:N�mero de d�as a buscar
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("dayofweek")) {
					case 1:
						measurement.getLabels().add("Sunday " + rs.getString("date"));
					break;
					case 2:
						measurement.getLabels().add("Monday " + rs.getString("date"));
					break;
					case 3:
						measurement.getLabels().add("Tuesday " + rs.getString("date"));
					break;
					case 4:
						measurement.getLabels().add("Wednesday " + rs.getString("date"));
					break;
					case 5:
						measurement.getLabels().add("Thursday " + rs.getString("date"));
					break;
					case 6:
						measurement.getLabels().add("Friday " + rs.getString("date"));;
					break;
					case 7:
						measurement.getLabels().add("Saturday " + rs.getString("date"));
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 
	 * @return The last week measurements
	 */
	public static ChartMeasurements getMonthStationSensorFromDB(int idStation, int sensorId)
	{
		ChartMeasurements measurement = new ChartMeasurements();
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetStationSensorMeasurementMonth(con);
			ps.setInt(1, idStation);
			ps.setInt(2, sensorId);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				measurement.getMinValues().add(rs.getInt("min"));
				measurement.getMaxValues().add(rs.getInt("max"));
				measurement.getAvgValues().add(rs.getInt("avg"));
				switch (rs.getInt("date")) {
					case 1:
						measurement.getLabels().add("Jan");
					break;
					case 2:
						measurement.getLabels().add("Feb");
					break;
					case 3:
						measurement.getLabels().add("Mar");
					break;
					case 4:
						measurement.getLabels().add("Apr");
					break;
					case 5:
						measurement.getLabels().add("May");
					break;
					case 6:
						measurement.getLabels().add("Jun");
					break;
					case 7:
						measurement.getLabels().add("Jul");
					break;
					case 8:
						measurement.getLabels().add("Aug");
					break;
					case 9:
						measurement.getLabels().add("Sep");
					break;
					case 10:
						measurement.getLabels().add("Oct");
					break;
					case 11:
						measurement.getLabels().add("Nov");
					break;
					case 12:
						measurement.getLabels().add("Dec");
					break;
				}
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			measurement = new ChartMeasurements();	
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			measurement = new ChartMeasurements();	
		} finally
		{
			conector.closeConnection(con);
		}
		return measurement;
	}
	
	/**
	 * 	
	 * @param idStation
	 * @return Arraylist with the measurements
	 */
	public static String getWeatherForecast(int idStation)
	{
		String forecast  = "";
		
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.GetInfoFromStation(con);
			ps.setInt(1, idStation);
			Log.log.info("Query=> {}", ps.toString());
			ResultSet rs = ps.executeQuery();
			if (rs.next())
			{
				String lat=rs.getString("LATITUDE");
				String lon=rs.getString("LONGITUDE");
				
				forecast = ThreadWeatherForecast.obtainWeatherString(lat, lon); 
				
			}	
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
			forecast  = "";
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
			forecast  = "";
		} finally
		{
			conector.closeConnection(con);
		}
		return forecast;
	}
	
	
	public static void storeNewMeasurement(Topics newTopic)
	{
		ConectionDDBB conector = new ConectionDDBB();
		Connection con = null;
		try
		{
			con = conector.obtainConnection(true);
			Log.log.debug("Database Connected");
			
			PreparedStatement ps = ConectionDDBB.InsertnewMeasurement(con);
			ps.setString(1, newTopic.getIdStation());
			ps.setString(2, newTopic.getIdSensor());
	        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			ps.setString(3, sdf.format(timestamp));
			ps.setString(4, newTopic.getValue());
			ps.setString(5, newTopic.getIdStation());
			ps.setString(6, newTopic.getIdSensor());
			ps.setString(7, sdf.format(timestamp));
			ps.setString(8, newTopic.getValue());
			Log.log.info("Query to store Measurement=> {}", ps.toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Log.log.error("Error: {}", e);
		} catch (NullPointerException e)
		{
			Log.log.error("Error: {}", e);
		} catch (Exception e)
		{
			Log.log.error("Error:{}", e);
		} finally
		{
			conector.closeConnection(con);
		}
	}
	
	
	
}
