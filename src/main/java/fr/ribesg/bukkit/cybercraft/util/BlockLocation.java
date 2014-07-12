package fr.ribesg.bukkit.cybercraft.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public final class BlockLocation {

	private final String worldName;
	private final int    x, y, z;

	public BlockLocation(final Location loc) {
		this(loc.getWorld().getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}

	public BlockLocation(final String worldName, final int x, final int y, final int z) {
		this.worldName = worldName;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public String getWorldName() {
		return this.worldName;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public Location toBukkit() {
		return new Location(Bukkit.getWorld(this.worldName), this.x, this.y, this.z);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		final BlockLocation that = (BlockLocation) o;

		if (this.x != that.x) {
			return false;
		}
		if (this.y != that.y) {
			return false;
		}
		if (this.z != that.z) {
			return false;
		}
		if (!this.worldName.equals(that.worldName)) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = this.worldName.hashCode();
		result = 31 * result + this.x;
		result = 31 * result + this.y;
		result = 31 * result + this.z;
		return result;
	}
}
