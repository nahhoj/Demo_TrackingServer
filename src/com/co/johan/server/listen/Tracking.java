package com.co.johan.server.listen;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tracking {

	private static String idunit = "";
	private static String latitude = "";
	private static String longitude = "";
	private static String datetime = "";
	//private static String event = "";
	private static String altitude = "";
	private static String speed = "";
	private static String ip = "";
	private static int idClient = 0;

	public static void insert(String message,String _ip,String _port) throws ClassNotFoundException {
		System.out.println(message);
		String[] msg = message.split("\\|");
		ip=_ip;
		if (msg.length == 7) {// message sent for Mobiles
			idunit = msg[0];
			idunit = idunit.replace("'", "''");
			datetime = msg[2];
			Date d = new Date(Long.parseLong(datetime));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			datetime = formatter.format(d);
			latitude = converLatLon(msg[3], true);
			longitude = converLatLon(msg[4], false);
			altitude = msg[5];
			speed = msg[6];
			String speedd = "";
			for (int x = 0; x < speed.length(); x++) {
				if (speed.charAt(x) >= '0')
					speedd = speedd + speed.charAt(x);
			}
			speed = speedd;
		} else {// message sent for Units

		}
		String query = "SELECT idclient from public.units where idunit like '" + idunit + "';";
		try {
			ResultSet rs = Posgresql.select(query);
			while (rs.next())
				idClient = rs.getInt("idclient");
			if (idClient > 0) {
				String sql = "INSERT INTO public.\"Events\"(idunit, ip, latitude, longitude, speed, datetime, event, idclient)VALUES ('"
						+ idunit + "','" + ip + "','" + latitude + "', '" + longitude + "', " + speed + ", '" + datetime
						+ "', '0'," + idClient + ");";
				if (!Posgresql.insert(sql)) {
					System.out.println("Event registered");
				} else {
					System.out.println("the event cannot be registered");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static String converLatLon(String latlon, final boolean lalo) {
		final int inte = (int) Float.parseFloat(latlon);
		float floa = Float.parseFloat(latlon) - inte;
		floa *= 60.0f;
		if (Math.abs(floa) < 10.0f) {
			latlon = "0" + inte + "0" + floa;
		} else {
			latlon = "0" + inte + "" + floa;
		}
		if (inte < 0 && lalo) {
			latlon += "S";
		} else if (inte >= 0 && lalo) {
			latlon += "N";
		} else if (inte < 0 && !lalo) {
			latlon += "W";
		} else {
			latlon += "E";
		}
		latlon = latlon.replace("-", "");
		return latlon;
	}
}
