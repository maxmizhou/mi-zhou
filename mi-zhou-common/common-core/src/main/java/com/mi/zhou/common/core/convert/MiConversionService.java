package com.mi.zhou.common.core.convert;

import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.lang.Nullable;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author Mi
 */
public class MiConversionService extends DefaultFormattingConversionService {
	@Nullable
	private static volatile MiConversionService SHARED_INSTANCE;

	public MiConversionService() {
		super();
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link MiConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 *
	 * @return the shared {@code MiConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		MiConversionService sharedInstance = MiConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (MiConversionService.class) {
				sharedInstance = MiConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new MiConversionService();
					MiConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
