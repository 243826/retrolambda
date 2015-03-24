// Copyright © 2013-2015 Esko Luontola <www.orfjackal.net>
// This software is released under the Apache License 2.0.
// The license text is at http://www.apache.org/licenses/LICENSE-2.0

package net.orfjackal.retrolambda.interfaces;

import com.google.common.base.MoreObjects;

import java.util.Objects;

public abstract class MethodKind {

    private MethodKind() {
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MethodKind)) {
            return false;
        }
        MethodKind that = (MethodKind) obj;
        return this.getClass() == that.getClass();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }


    public static class Abstract extends MethodKind {
    }

    public static class Concrete extends MethodKind {
    }

    public static class Default extends MethodKind {

        public final MethodRef defaultImpl;

        public Default(MethodRef defaultImpl) {
            this.defaultImpl = defaultImpl;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Default)) {
                return false;
            }
            Default that = (Default) obj;
            return this.defaultImpl.equals(that.defaultImpl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(defaultImpl);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .addValue(defaultImpl)
                    .toString();
        }
    }
}
