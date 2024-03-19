/*
 *     SystemInfo - The Master of Server Hardware
 *     Copyright © 2024 CMarco
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.cmarco.systeminfo.protocol;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * Immutable class representing network statistics data.
 */
public final class NetworkStatsData {
    private final long lastSentPackets, lastReceivedPackets, lastSentBytes, lastReceivedBytes;
    private final long totalSentPackets, totalReceivedPackets, totalSentBytes, totalReceivedBytes;
    private final long lastReset;

    /**
     * Private constructor to enforce the use of the builder pattern.
     *
     * @param builder The builder instance used to construct this object.
     */
    private NetworkStatsData(@NotNull final Builder builder) {
        this.lastSentPackets = builder.lastSentPackets;
        this.lastReceivedPackets = builder.lastReceivedPackets;
        this.lastSentBytes = builder.lastSentBytes;
        this.lastReceivedBytes = builder.lastReceivedBytes;
        this.totalSentPackets = builder.totalSentPackets;
        this.totalReceivedPackets = builder.totalReceivedPackets;
        this.totalSentBytes = builder.totalSentBytes;
        this.totalReceivedBytes = builder.totalReceivedBytes;
        this.lastReset = builder.lastReset;
    }

    /**
     * Gets the number of packets last sent.
     *
     * @return The number of packets last sent.
     */
    public long getLastSentPackets() {
        return lastSentPackets;
    }

    /**
     * Gets the number of packets last received.
     *
     * @return The number of packets last received.
     */
    public long getLastReceivedPackets() {
        return lastReceivedPackets;
    }

    /**
     * Gets the number of bytes last sent.
     *
     * @return The number of bytes last sent.
     */
    public long getLastSentBytes() {
        return lastSentBytes;
    }

    /**
     * Gets the number of bytes last received.
     *
     * @return The number of bytes last received.
     */
    public long getLastReceivedBytes() {
        return lastReceivedBytes;
    }

    /**
     * Gets the total number of packets sent.
     *
     * @return The total number of packets sent.
     */
    public long getTotalSentPackets() {
        return totalSentPackets;
    }

    /**
     * Gets the total number of packets received.
     *
     * @return The total number of packets received.
     */
    public long getTotalReceivedPackets() {
        return totalReceivedPackets;
    }

    /**
     * Gets the total number of bytes sent.
     *
     * @return The total number of bytes sent.
     */
    public long getTotalSentBytes() {
        return totalSentBytes;
    }

    /**
     * Gets the total number of bytes received.
     *
     * @return The total number of bytes received.
     */
    public long getTotalReceivedBytes() {
        return totalReceivedBytes;
    }

    /**
     * Gets the timestamp of the last reset.
     *
     * @return The timestamp of the last reset.
     */
    public long getLastReset() {
        return lastReset;
    }

    /**
     * Builder class for constructing {@link NetworkStatsData} instances.
     */
    public static class Builder {
        private long lastSentPackets, lastReceivedPackets, lastSentBytes, lastReceivedBytes;
        private long totalSentPackets, totalReceivedPackets, totalSentBytes, totalReceivedBytes;
        private long lastReset;

        /**
         * Constructs a new {@link Builder} instance.
         */
        public Builder() {
            // Set default values if needed
        }

        /**
         * Sets the number of packets last sent.
         *
         * @param lastSentPackets The number of packets last sent.
         * @return This builder instance.
         */
        public Builder lastSentPackets(@Range(from = 0, to = Long.MAX_VALUE) final long lastSentPackets) {
            this.lastSentPackets = lastSentPackets;
            return this;
        }

        /**
         * Sets the number of packets last received.
         *
         * @param lastReceivedPackets The number of packets last received.
         * @return This builder instance.
         */
        public Builder lastReceivedPackets(@Range(from = 0, to = Long.MAX_VALUE) final long lastReceivedPackets) {
            this.lastReceivedPackets = lastReceivedPackets;
            return this;
        }

        /**
         * Sets the number of bytes last sent.
         *
         * @param lastSentBytes The number of bytes last sent.
         * @return This builder instance.
         */
        public Builder lastSentBytes(@Range(from = 0, to = Long.MAX_VALUE) final long lastSentBytes) {
            this.lastSentBytes = lastSentBytes;
            return this;
        }

        /**
         * Sets the number of bytes last received.
         *
         * @param lastReceivedBytes The number of bytes last received.
         * @return This builder instance.
         */
        public Builder lastReceivedBytes(@Range(from = 0, to = Long.MAX_VALUE) final long lastReceivedBytes) {
            this.lastReceivedBytes = lastReceivedBytes;
            return this;
        }

        /**
         * Sets the total number of packets sent.
         *
         * @param totalSentPackets The total number of packets sent.
         * @return This builder instance.
         */
        public Builder totalSentPackets(@Range(from = 0, to = Long.MAX_VALUE) final long totalSentPackets) {
            this.totalSentPackets = totalSentPackets;
            return this;
        }

        /**
         * Sets the total number of packets received.
         *
         * @param totalReceivedPackets The total number of packets received.
         * @return This builder instance.
         */
        public Builder totalReceivedPackets(@Range(from = 0, to = Long.MAX_VALUE) final long totalReceivedPackets) {
            this.totalReceivedPackets = totalReceivedPackets;
            return this;
        }

        /**
         * Sets the total number of bytes sent.
         *
         * @param totalSentBytes The total number of bytes sent.
         * @return This builder instance.
         */
        public Builder totalSentBytes(@Range(from = 0, to = Long.MAX_VALUE) final long totalSentBytes) {
            this.totalSentBytes = totalSentBytes;
            return this;
        }

        /**
         * Sets the total number of bytes received.
         *
         * @param totalReceivedBytes The total number of bytes received.
         * @return This builder instance.
         */
        public Builder totalReceivedBytes(@Range(from = 0, to = Long.MAX_VALUE) final long totalReceivedBytes) {
            this.totalReceivedBytes = totalReceivedBytes;
            return this;
        }

        /**
         * Sets the timestamp of the last reset.
         *
         * @param lastReset The timestamp of the last reset.
         * @return This builder instance.
         */
        public Builder lastReset(@Range(from = 0, to = Long.MAX_VALUE) final long lastReset) {
            this.lastReset = lastReset;
            return this;
        }

        /**
         * Builds a new {@link NetworkStatsData} instance.
         *
         * @return The constructed {@link NetworkStatsData} instance.
         */
        public NetworkStatsData build() {
            return new NetworkStatsData(this);
        }
    }
}

