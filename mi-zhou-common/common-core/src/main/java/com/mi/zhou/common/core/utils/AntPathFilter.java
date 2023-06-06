

package com.mi.zhou.common.core.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;
import java.io.FileFilter;
import java.io.Serial;
import java.io.Serializable;

/**
 * spring AntPath 规则文件过滤
 *
 * @author Mi
 */
@RequiredArgsConstructor
public class AntPathFilter implements FileFilter, Serializable {
	@Serial
	private static final long serialVersionUID = 812598009067554612L;
	private static final PathMatcher PATH_MATCHER = new AntPathMatcher();

	private final String pattern;

	@Override
	public boolean accept(File pathname) {
		String filePath = pathname.getAbsolutePath();
		return PATH_MATCHER.match(pattern, filePath);
	}
}
