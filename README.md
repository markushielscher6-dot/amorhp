# Armor HP (Fabric, Minecraft 26.3)

Zeigt neben dem Inventar die verbleibende Haltbarkeit deiner Rüstung als Zahl
(z. B. `412/592`), farbig: grün (> 50 %), gelb (> 25 %), rot (darunter).

## Jar bauen

Du brauchst Java 25 und Gradle 9.5.1 (oder GitHub, siehe unten).

    gradle build

Die fertige Mod liegt dann in `build/libs/armorhp-1.0.0.jar`.

### Ohne Installation: über GitHub

1. Projekt in ein neues GitHub-Repository hochladen.
2. Reiter „Actions" öffnen, den Workflow „build" laufen lassen.
3. Das Jar unter „Artifacts" herunterladen.

## Installieren (Modrinth App)

1. Profil mit **Minecraft 26.3** und **Fabric** erstellen.
2. **Fabric API** hinzufügen.
3. `armorhp-1.0.0.jar` in den Mods-Ordner des Profils legen.
