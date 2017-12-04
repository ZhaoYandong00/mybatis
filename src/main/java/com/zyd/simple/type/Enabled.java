package com.zyd.simple.type;

public enum Enabled {
	/**
	 * 禁用
	 */
	disabled(0),
	/**
	 * 启用
	 */
	enabled(1);
	private final int value;

	private Enabled(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
