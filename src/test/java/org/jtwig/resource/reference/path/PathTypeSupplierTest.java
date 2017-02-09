package org.jtwig.resource.reference.path;

import com.google.common.base.Function;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PathTypeSupplierTest {
    private Function<String, String> systemProperties = mock(Function.class);

    @Test
    public void windows() throws Exception {
        given(systemProperties.apply("os.name")).willReturn("win");
        PathTypeSupplier underTest = new PathTypeSupplier(systemProperties);

        PathType result = underTest.get();

        assertThat(result, is(PathType.UNC));
    }

    @Test
    public void mac() throws Exception {
        given(systemProperties.apply("os.name")).willReturn("mac");
        PathTypeSupplier underTest = new PathTypeSupplier(systemProperties);

        PathType result = underTest.get();

        assertThat(result, is(PathType.POSIX));
    }
    @Test
    public void linux() throws Exception {
        given(systemProperties.apply("os.name")).willReturn("nix");
        PathTypeSupplier underTest = new PathTypeSupplier(systemProperties);

        PathType result = underTest.get();

        assertThat(result, is(PathType.POSIX));
    }
    @Test
    public void solaris() throws Exception {
        given(systemProperties.apply("os.name")).willReturn("sunos");
        PathTypeSupplier underTest = new PathTypeSupplier(systemProperties);

        PathType result = underTest.get();

        assertThat(result, is(PathType.POSIX));
    }
}