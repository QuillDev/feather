package moe.quill.feather.old.selections.zones.utils;

import moe.quill.feather.old.selections.zones.Zone;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public class LocationUtils {

    public static Location getMinLocation(Location firstLocation, Location secondLocation) {
        return new Location(firstLocation.getWorld(),
                Math.min(firstLocation.getX(), secondLocation.getX()),
                Math.min(firstLocation.getY(), secondLocation.getY()),
                Math.min(firstLocation.getZ(), secondLocation.getZ())
        );
    }


    public static Location getMaxLocation(Location firstLocation, Location secondLocation) {
        return new Location(firstLocation.getWorld(),
                Math.max(firstLocation.getX(), secondLocation.getX()),
                Math.max(firstLocation.getY(), secondLocation.getY()),
                Math.max(firstLocation.getZ(), secondLocation.getZ())
        );
    }

    /**
     * Get the closest location to the players location
     *
     * @param location  to check as the base location
     * @param locations to check the closest locations from
     * @return the closest location if it's applicable
     */
    public static Optional<Location> getClosestLocation(Location location, List<Location> locations) {
        if (locations.size() == 0) return Optional.empty();
        if (locations.size() == 1) return Optional.of(locations.get(0));

        var closestLocation = locations.get(0);
        var closestDistance = closestLocation.distance(location);
        for (int idx = 1; idx < locations.size(); idx++) {
            final var curLocation = locations.get(idx);
            final var curDistance = curLocation.distance(location);
            if (curDistance > closestDistance) continue;
            closestLocation = curLocation;
            closestDistance = curDistance;
        }

        return Optional.of(closestLocation);
    }

    /**
     * Check if the given location is within the given bounds
     *
     * @param minLocation   minimum point to use for checking
     * @param maxLocation   max point to use for checking
     * @param queryLocation the location to check if it's within the bounds
     * @param checkY        whether to check the y-axis of these bounds
     * @return whether the given point is within those bounds
     */
    public static boolean isWithinBounds(Location minLocation, Location maxLocation, Location queryLocation, boolean checkY) {

        final var withinX = (minLocation.getBlockX() <= queryLocation.getBlockX() && queryLocation.getBlockX() <= maxLocation.getBlockX());
        final var withinY = !checkY || (minLocation.getBlockY() <= queryLocation.getBlockY() && queryLocation.getBlockY() <= maxLocation.getBlockY());
        final var withinZ = (minLocation.getBlockZ() <= queryLocation.getBlockZ() && queryLocation.getBlockZ() <= maxLocation.getBlockZ());

        return withinX && withinY && withinZ;
    }

    public static boolean isWithinBounds(Location minLocation, Location maxLocation, Location queryLocation) {
        return isWithinBounds(minLocation, maxLocation, queryLocation, true);
    }

    /**
     * Get the component list of zones
     *
     * @param zones to print
     * @return the component zone list
     */
    public static Component getZoneListComponent(List<Zone> zones) {
        var output = Component.text();
        //Generate the zone list
        for (int idx = 0; idx < zones.size(); idx++) {
            final var zone = zones.get(idx);
            output.append(Component.text(zone.getName() + ": ")).append(Component.text(zone.toString()));

            //Append newlines before the last entry
            if (idx < zones.size() - 1) {
                output.append(Component.newline());
            }
        }

        return output.build();
    }
}
