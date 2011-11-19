package net.sf.xfresh.core;

/**
 * User: darl (darl@yandex-team.net)
 * Date: 3/21/11 8:16 PM
 */
public interface SaxWriter<T> {
    void write(T value, SaxHandler handler);
}
