package com.mi.zhou.common.core.beans;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * copy key
 *
 * @author Mi
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class MiBeanCopierKey {
	private final Class<?> source;
	private final Class<?> target;
	private final boolean useConverter;
	private final boolean nonNull;
}
