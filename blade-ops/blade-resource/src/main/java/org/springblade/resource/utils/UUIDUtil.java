package org.springblade.resource.utils;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString();
		result = result.toLowerCase().replaceAll("-", "");
		return result;
	}

}
