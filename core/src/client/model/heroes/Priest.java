package client.model.heroes;

import client.model.Player;
import client.model.skills.*;

public class Priest extends Hero {
    final int id;

    public Priest(Player owner, int y, int x) {
        super(owner, 110, 100, 50, 5, y, x);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Heal(true));
        this.skills.add(new SettingWall());
    }
    public String toString() {
        return "Priest";
    }
}
