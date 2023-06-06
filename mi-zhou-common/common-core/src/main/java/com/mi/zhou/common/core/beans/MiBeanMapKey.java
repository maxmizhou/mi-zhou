package com.mi.zhou.common.core.beans;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * bean map key，提高性能
 *
 * @author Mi
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class MiBeanMapKey {
	private final Class type;
	private final int require;
}
