<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Planets - OLog</title>
    <style type="text/css">
        .buildings-cell {
            text-align: center;
        }

        .fleet-cell {
            text-align: center;
        }

        .defences-cell {
            text-align: center;
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <th>Coordinate</th>
            <th>Player</th>
            <th>Player ID</th>
            <th>Planet</th>
            <th>Planet ID</th>
            <th>Metal Mine</th>
            <th>Crystal Mine</th>
            <th>Deuterium Synthesizer</th>
            <th>Solar Plant</th>
            <th>Fusion Reactor</th>
            <th>Solar Satellite</th>
            <th>Metal Storage</th>
            <th>Crystal Storage</th>
            <th>Deuterium Tank</th>
            <th>Robotics Factory</th>
            <th>Shipyard</th>
            <th>Research Lab</th>
            <th>Alliance Depot</th>
            <th>Missile Silo</th>
            <th>Nanite Factory</th>
            <th>Terraformer</th>
            <th>Light Fighter</th>
            <th>Heavy Fighter</th>
            <th>Cruiser</th>
            <th>Battleship</th>
            <th>Small Cargo</th>
            <th>Large Cargo</th>
            <th>Colony Ship</th>
            <th>Battlecruiser</th>
            <th>Bomber</th>
            <th>Destroyer</th>
            <th>Deathstar</th>
            <th>Recycler</th>
            <th>Espionage Probe</th>
            <th>Rocket Launcher</th>
            <th>Light Laser</th>
            <th>Heavy Laser</th>
            <th>Gauss Cannon</th>
            <th>Ion Cannon</th>
            <th>Plasma Turret</th>
            <th>Small Shield Dome</th>
            <th>Large Shield Dome</th>
            <th>Anti-Ballistic Missiles</th>
            <th>Interplanetary Missiles</th>
        </tr>
        <g:each in="${planets}">
            <tr>
                <td>[${it.currentCoordinate.galaxy}:${it.currentCoordinate.solarSystem}:${it.currentCoordinate.position}]</td>
                <td>${it.player.currentName}</td>
                <td>${it.player.playerId}</td>
                <td>${it.currentName}</td>
                <td>${it.planetId}</td>
                <td class="buildings-cell">${it.buildings.metalMine}</td>
                <td class="buildings-cell">${it.buildings.crystalMine}</td>
                <td class="buildings-cell">${it.buildings.deuteriumSynthesizer}</td>
                <td class="buildings-cell">${it.buildings.solarPlant}</td>
                <td class="buildings-cell">${it.buildings.fusionReactor}</td>
                <td class="buildings-cell">${it.buildings.solarSatellite}</td>
                <td class="buildings-cell">${it.buildings.metalStorage}</td>
                <td class="buildings-cell">${it.buildings.crystalStorage}</td>
                <td class="buildings-cell">${it.buildings.deuteriumTank}</td>
                <td class="buildings-cell">${it.buildings.roboticsFactory}</td>
                <td class="buildings-cell">${it.buildings.shipyard}</td>
                <td class="buildings-cell">${it.buildings.researchLab}</td>
                <td class="buildings-cell">${it.buildings.allianceDepot}</td>
                <td class="buildings-cell">${it.buildings.missileSilo}</td>
                <td class="buildings-cell">${it.buildings.naniteFactory}</td>
                <td class="buildings-cell">${it.buildings.terraformer}</td>
                <td class="fleet-cell">${it.fleet.lightFighter}</td>
                <td class="fleet-cell">${it.fleet.heavyFighter}</td>
                <td class="fleet-cell">${it.fleet.cruiser}</td>
                <td class="fleet-cell">${it.fleet.battleship}</td>
                <td class="fleet-cell">${it.fleet.smallCargo}</td>
                <td class="fleet-cell">${it.fleet.largeCargo}</td>
                <td class="fleet-cell">${it.fleet.colonyShip}</td>
                <td class="fleet-cell">${it.fleet.battlecruiser}</td>
                <td class="fleet-cell">${it.fleet.bomber}</td>
                <td class="fleet-cell">${it.fleet.destroyer}</td>
                <td class="fleet-cell">${it.fleet.deathstar}</td>
                <td class="fleet-cell">${it.fleet.recycler}</td>
                <td class="fleet-cell">${it.fleet.espionageProbe}</td>
                <td class="defences-cell">${it.defences.rocketLauncher}</td>
                <td class="defences-cell">${it.defences.lightLaser}</td>
                <td class="defences-cell">${it.defences.heavyLaser}</td>
                <td class="defences-cell">${it.defences.gaussCannon}</td>
                <td class="defences-cell">${it.defences.ionCannon}</td>
                <td class="defences-cell">${it.defences.plasmaTurret}</td>
                <td class="defences-cell">${it.defences.smallShieldDome}</td>
                <td class="defences-cell">${it.defences.largeShieldDome}</td>
                <td class="defences-cell">${it.defences.antiBallisticMissiles}</td>
                <td class="defences-cell">${it.defences.interplanetaryMissiles}</td>
            </tr>
        </g:each>
    </table>
</body>
</html>