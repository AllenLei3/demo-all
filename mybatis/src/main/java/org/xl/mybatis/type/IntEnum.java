package org.xl.mybatis.type;

/**
 * @author xulei
 */
public enum IntEnum {

    ONE(23),

    TWO(24),

    THREE(25);

    private final int code;

    IntEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static IntEnum get(int code) {
        for (IntEnum value : IntEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }
}
