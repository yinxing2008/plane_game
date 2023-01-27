/**
 * 厦门大学计算机专业 | 前华为工程师
 * 专注《零基础学编程系列》  http://lblbc.cn/blog
 * 包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
 * 公众号：蓝不蓝编程
 */
package cn.lblbc.game;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.lblbc.game.sprite.Bullet;
import cn.lblbc.game.sprite.EnemyPlane;
import cn.lblbc.game.sprite.Explosion;
import cn.lblbc.game.sprite.MyPlane;
import cn.lblbc.game.sprite.Sprite;

public class SpriteManager {
    private final static SpriteManager instance = new SpriteManager();
    private List<Sprite> sprites = new ArrayList<>();//存放所有战机、子弹、爆炸对象
    private List<Sprite> spritesToBeAdded = new ArrayList<>();//待加入sprites的临时队列
    private List<Sprite> enemyPlanePool = new ArrayList<>();//敌机对象池
    private List<Sprite> explosionPool = new ArrayList<>();//爆炸对象池
    private List<Sprite> bulletPool = new ArrayList<>(); //子弹对象池
    private Sprite myPlane;//我方战机

    private SpriteManager() {
    }

    public static SpriteManager getInstance() {
        return instance;
    }

    public void init(Context context) {
        BitMapReader bitMapReader = BitMapReader.getInstance();
        bitMapReader.init(context);
        //初始化我方战机
        myPlane = new MyPlane(bitMapReader.getMyPlaneBitmap());

        //初始化30架敌机
        for (int i = 0; i < 30; i++) {
            Sprite sprite = new EnemyPlane(bitMapReader.getEnemyPlaneBitmap());
            enemyPlanePool.add(sprite);
        }

        //初始化30个爆炸对象
        for (int i = 0; i < 30; i++) {
            Sprite sprite = new Explosion(bitMapReader.getExplosionBitmap());
            explosionPool.add(sprite);
        }

        //初始化20颗子弹
        for (int i = 0; i < 20; i++) {
            Sprite sprite = new Bullet(bitMapReader.getBulletBitmap());
            bulletPool.add(sprite);
        }
    }

    public void addMyPlane() {
        spritesToBeAdded.add(myPlane);
    }

    public void createEnemyPlane(int canvasWidth) {
        Sprite sprite = getEnemyPlane(canvasWidth);
        if (sprite != null) {
            spritesToBeAdded.add(sprite);
        }
    }

    public Sprite getEnemyPlane(int canvasWidth) {
        if (!enemyPlanePool.isEmpty()) {
            Sprite sprite = enemyPlanePool.remove(0);
            float spriteWidth = sprite.getWidth();
            float spriteHeight = sprite.getHeight();
            float x = (float) ((canvasWidth - spriteWidth) * Math.random());
            float y = -spriteHeight;
            sprite.setX(x);
            sprite.setY(y);

            return sprite;
        } else {
            return null;
        }
    }

    public void addBullet(float x, float y) {
        Sprite sprite = getBullet(x, y);
        if (sprite != null) {
            spritesToBeAdded.add(sprite);
        }
    }

    public void addExplosion(float centerX, float centerY) {
        Sprite sprite = getExplosion(centerX, centerY);
        if (sprite != null) {
            spritesToBeAdded.add(sprite);
        }
    }

    public Sprite getExplosion(float centerX, float centerY) {
        if (!explosionPool.isEmpty()) {
            Sprite sprite = explosionPool.remove(0);
            sprite.moveToByCenter(centerX, centerY);
            return sprite;
        } else {
            return null;
        }
    }

    public Sprite getBullet(float x, float y) {
        if (!bulletPool.isEmpty()) {
            Sprite sprite = bulletPool.remove(0);
            sprite.moveTo(x, y);
            return sprite;
        } else {
            return null;
        }
    }

    public List<Sprite> getSprites() {
        //将spritesNeedAdded添加到sprites中,界面渲染时都从sprites读取对象
        if (spritesToBeAdded.size() > 0) {
            sprites.addAll(spritesToBeAdded);
            spritesToBeAdded.clear();
        }

        return sprites;
    }

    public List<Sprite> getVisibleBullets() {
        List<Sprite> bullets = new ArrayList<>();
        for (Sprite s : sprites) {
            if (s.isVisible() && s instanceof Bullet) {
                Bullet bullet = (Bullet) s;
                bullets.add(bullet);
            }
        }
        return bullets;
    }

    public List<Sprite> getVisibleEnemyPlanes() {
        List<Sprite> enemyPlanes = new ArrayList<>();
        for (Sprite sprite : sprites) {
            if (sprite.isVisible() && sprite instanceof EnemyPlane) {
                enemyPlanes.add(sprite);
            }
        }
        return enemyPlanes;
    }

    public Sprite getMyPlane() {
        return myPlane;
    }

    /**
     * 回收已隐藏的Sprite
     */
    public void recycleHiddenSprites() {
        Iterator<Sprite> iterator = sprites.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = iterator.next();
            if (!sprite.isVisible()) {
                iterator.remove();
                sprite.show();
                if (sprite instanceof Bullet) {
                    bulletPool.add(sprite);
                } else if (sprite instanceof EnemyPlane) {
                    enemyPlanePool.add(sprite);
                } else if (sprite instanceof Explosion) {
                    explosionPool.add(sprite);
                }
            }
        }
    }

    public void cleanUp() {
        //隐藏战机、子弹、爆炸
        for (Sprite s : sprites) {
            s.hide();
        }
        sprites.clear();
        spritesToBeAdded.clear();
        enemyPlanePool.clear();
        bulletPool.clear();
        explosionPool.clear();
    }
}
