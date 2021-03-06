package client.model.heroes;

import client.model.Player;
import client.model.skills.*;

public class Wizard extends Hero {
    final int id;

    public Wizard(Player owner, int y, int x) {
        super(owner, 110, 90, 90, 5, y, x,120);
        id = idGen++;
        this.skills.add(new Walk(3));
        this.skills.add(new Stay());
        this.skills.add(new Fireball());
        this.skills.add(new SettingTrap());
        this.skills.add(new Teleport());
    }
}