<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Moons - OLog</title>
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
            <th>Moon</th>
            <th>Moon ID</th>
            <th>Solar Satellite</th>
            <th>Metal Storage</th>
            <th>Crystal Storage</th>
            <th>Deuterium Tank</th>
            <th>Robotics Factory</th>
            <th>Shipyard</th>
            <th>Lunar Base</th>
            <th>Sensor Phalanx</th>
            <th>Jump Gate</th>
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
        <g:each in="${moons}">
            <tr>
                <td>[${it.currentCoordinate.galaxy}:${it.currentCoordinate.solarSystem}:${it.currentCoordinate.position}]</td>
                <td>${it.player.currentName}</td>
                <td>${it.player.playerId}</td>
                <td>${it.currentName}</td>
                <td>${it.moonId}</td>
                <td class="buildings-cell">${it.buildings.solarSatellite}</td>
                <td class="buildings-cell">${it.buildings.metalStorage}</td>
                <td class="buildings-cell">${it.buildings.crystalStorage}</td>
                <td class="buildings-cell">${it.buildings.deuteriumTank}</td>
                <td class="buildings-cell">${it.buildings.roboticsFactory}</td>
                <td class="buildings-cell">${it.buildings.shipyard}</td>
                <td class="buildings-cell">${it.buildings.lunarBase}</td>
                <td class="buildings-cell">${it.buildings.sensorPhalanx}</td>
                <td class="buildings-cell">${it.buildings.jumpGate}</td>
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