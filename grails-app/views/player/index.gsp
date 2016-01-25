<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Players - OLog</title>
    <style type="text/css">
        .researches-cell {
            text-align: center;
        }
    </style>
</head>
<body>
    <table>
        <tr>
            <th>Player</th>
            <th>Player ID</th>
            <th>Energy Technology</th>
            <th>Laser Technology</th>
            <th>Ion Technology</th>
            <th>Hyperspace Technology</th>
            <th>Plasma Technology</th>
            <th>Combustion Drive</th>
            <th>Impulse Drive</th>
            <th>Hyperspace Drive</th>
            <th>Espionage Technology</th>
            <th>Computer Technology</th>
            <th>Astrophysics</th>
            <th>Intergalactic Research Network</th>
            <th>Graviton</th>
            <th>Weapons Technology</th>
            <th>Shielding Technology</th>
            <th>Armour Technology</th>
        </tr>
        <g:each in="${players}">
            <tr>
                <td>${it.currentName}</td>
                <td>${it.playerId}</td>
                <td class="researches-cell">${it.researches.energyTechnology}</td>
                <td class="researches-cell">${it.researches.laserTechnology}</td>
                <td class="researches-cell">${it.researches.ionTechnology}</td>
                <td class="researches-cell">${it.researches.hyperspaceTechnology}</td>
                <td class="researches-cell">${it.researches.plasmaTechnology}</td>
                <td class="researches-cell">${it.researches.combustionDrive}</td>
                <td class="researches-cell">${it.researches.impulseDrive}</td>
                <td class="researches-cell">${it.researches.hyperspaceDrive}</td>
                <td class="researches-cell">${it.researches.espionageTechnology}</td>
                <td class="researches-cell">${it.researches.computerTechnology}</td>
                <td class="researches-cell">${it.researches.astrophysics}</td>
                <td class="researches-cell">${it.researches.intergalacticResearchNetwork}</td>
                <td class="researches-cell">${it.researches.gravitonTechnology}</td>
                <td class="researches-cell">${it.researches.weaponsTechnology}</td>
                <td class="researches-cell">${it.researches.shieldingTechnology}</td>
                <td class="researches-cell">${it.researches.armourTechnology}</td>
            </tr>
        </g:each>
    </table>
</body>
</html>