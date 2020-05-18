package Client.GUI.Screens;

import Client.GUI.Utility.Constants;
import Client.GUI.SwordGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import java.util.ArrayList;

public class LoadScreen implements Screen {

    private SwordGame swordGame;
    private Stage stage;
    private Texture paladinTexture, warriorTexture, archerTexture, necromancerTexture, priestTexture, wizardTexture,
            paladinTexture_dark, warriorTexture_dark, archerTexture_dark, necromancerTexture_dark, priestTexture_dark, wizardTexture_dark,
            background;
    private ArrayList<Texture> textures_white;
    private ArrayList<Texture> textures_dark;
    private TextField ipField;
    private TextField portField;
    private TextField nickField;


    /**
     * Constructor which take sword game to use batch etc...
     *
     * @param swordGame instance of swordgame
     */
    public LoadScreen(SwordGame swordGame) {
        this.swordGame = swordGame;
        stage = new Stage();  //initialize stage
        loadData(); //load all textures using AssetManager
        Gdx.input.setInputProcessor(stage);  //set stage as a input processor
        init();
    }

    /**
     * Init all methods
     */
    private void init() {
        texturesToArrays();
        addBackground();
        heroes();
        nextScreenButton();
        nickInput();
        ipInput();
        portInput();
    }

    private void texturesToArrays() {
        textures_white = new ArrayList<>();
        textures_white.add(archerTexture);
        textures_white.add(necromancerTexture);
        textures_white.add(paladinTexture);
        textures_white.add(priestTexture);
        textures_white.add(warriorTexture);
        textures_white.add(wizardTexture);

        textures_dark = new ArrayList<>();
        textures_dark.add(archerTexture_dark);
        textures_dark.add(necromancerTexture_dark);
        textures_dark.add(paladinTexture_dark);
        textures_dark.add(priestTexture_dark);
        textures_dark.add(warriorTexture_dark);
        textures_dark.add(wizardTexture_dark);
    }

    private void addBackground() {
        TextureRegion textureRegion = new TextureRegion(background);
        final Image background = new Image(textureRegion);
        background.setSize(Constants.WIDTH, Constants.HEIGHT);
        background.setPosition(0, 0);
        stage.addActor(background);
    }

    private void portInput() {
        portField = new TextField("", swordGame.skin);
        portField.setMessageText("Port");
        portField.setPosition(280, 100);
        portField.setSize(200, 40);
        stage.addActor(portField);
        swordGame.port = portField.getText();
    }

    private void ipInput() {
        ipField = new TextField("", swordGame.skin);
        ipField.setMessageText("IP");
        ipField.setPosition(50, 100);
        ipField.setSize(200, 40);
        stage.addActor(ipField);
        swordGame.ip = ipField.getText();
    }


    private void nickInput() {
        nickField = new TextField("", swordGame.skin);
        nickField.setMessageText("Nick");
        nickField.setPosition(510, 100);
        nickField.setSize(200, 40);
        stage.addActor(nickField);
        swordGame.nick = nickField.getText();
    }

    private void heroes() {
        int y = 400;        //y param of all heroes
        int x = 10;         //start x of heroes positions (then add 170 to all)
        int size = 150;     //size of a texture
        addHeroe(archerTexture_dark, x, y, size);
        addHeroe(necromancerTexture_dark, x += 170, y, size);
        addHeroe(paladinTexture_dark, x += 170, y, size);
        addHeroe(priestTexture_dark, x += 170, y, size);
        addHeroe(warriorTexture_dark, x += 170, y, size);
        addHeroe(wizardTexture_dark, x += 170, y, size);
    }

    private void addHeroe(final Texture texture, int x, int y, int size) {
        TextureRegion textureRegion = new TextureRegion(texture);
        final Image background = new Image(textureRegion);
        background.setSize(size, size);
        background.setPosition(x, y);
        background.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                int i;
                for (i = 0; i < textures_dark.size(); i++) {
                    if (texture == textures_dark.get(i))
                        break;
                }
                if (!swordGame.chosen[i]) {
                    swordGame.chosen[i] = true;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_white.get(i))));
                } else {
                    swordGame.chosen[i] = false;
                    background.setDrawable(new SpriteDrawable(new Sprite(textures_dark.get(i))));
                }
                return true;
            }
        });
        stage.addActor(background);
    }


    private boolean amountTrue() {
        int count = 0;
        for (int i = 0; i < swordGame.chosen.length; i++) {
            if (swordGame.chosen[i])
                count++;
        }
        return count == 4;
    }


    private void nextScreenButton() {
        TextButton button = new TextButton("Next", swordGame.skin);
        button.setSize(100, 100);
        button.setPosition(750, 50);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    swordGame.ip = ipField.getText();
                    swordGame.nick = nickField.getText();
                    swordGame.port = portField.getText();
                    if (amountTrue() && !swordGame.ip.equals("") & !swordGame.nick.equals("") & !swordGame.port.equals("")) {  //TODO check ip and else...
                        swordGame.setScreen(new PlayScreen(swordGame));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        stage.addActor(button);
    }



    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        swordGame.batch.begin();
        stage.draw();
        swordGame.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void show() {

    }

    private void loadData() {
        paladinTexture = swordGame.assets.manager.get("heroes/paladin.png", Texture.class);
        warriorTexture = swordGame.assets.manager.get("heroes/warrior.png", Texture.class);
        archerTexture = swordGame.assets.manager.get("heroes/archer.png", Texture.class);
        necromancerTexture = swordGame.assets.manager.get("heroes/necromancer.png", Texture.class);
        wizardTexture = swordGame.assets.manager.get("heroes/wizard.png", Texture.class);
        priestTexture = swordGame.assets.manager.get("heroes/priest.png", Texture.class);

        paladinTexture_dark = swordGame.assets.manager.get("heroes/paladin_nygga.png", Texture.class);
        warriorTexture_dark = swordGame.assets.manager.get("heroes/warrior_nygga.png", Texture.class);
        archerTexture_dark = swordGame.assets.manager.get("heroes/archer_nygga.png", Texture.class);
        necromancerTexture_dark = swordGame.assets.manager.get("heroes/necromancer_nygga.png", Texture.class);
        priestTexture_dark = swordGame.assets.manager.get("heroes/priest_nygga.png", Texture.class);
        wizardTexture_dark = swordGame.assets.manager.get("heroes/wizard_nygga.png", Texture.class);

        background = swordGame.assets.manager.get("special/background.png", Texture.class);
    }
}