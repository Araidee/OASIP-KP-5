package com.example.backend221.services;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.security.crypto.password.PasswordEncoder;

    /**
     * Argon2 PasswordEncoder implementation {@link PasswordEncoder}
     * based on the argon2-jvm library with recommended default parameters.
     *
     * @see <a href="https://github.com/phxql/argon2-jvm">https://github.com/phxql/argon2-jvm</a> {}
     *
     * @author Serdar Bellikli
     */
    public class Argon2PasswordEncoder implements PasswordEncoder {

        /*
         * Recommended parameters
         */
        private static final int ITERATIONS = 2;
        private static final int MEMORY = 65536;
        private static final int PARALLELISM = 1;

        /*
         * Recommended argon2 type
         */
        public static final Argon2Factory.Argon2Types TYPE = Argon2Factory.Argon2Types.ARGON2i;

        /*
         * Thread safe singleton instance
         */
        private static final Argon2 INSTANCE = Argon2Factory.create(TYPE);

        /**
         * {@inheritDoc}
         *
         * Hashes raw password using argon2id
         */
        @Override
        public String encode(CharSequence rawPassword) {
            return INSTANCE.hash(ITERATIONS, MEMORY, PARALLELISM, rawPassword.toString());
        }

        /**
         * {@inheritDoc}
         *
         * Compares raw password with encoded one using argon2id
         */
        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return INSTANCE.verify(encodedPassword, rawPassword.toString());
        }
    }

