using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace PlaneGame
{
    class GameManager
    {
        private GameManager() { }

        private static GameManager _instance = null;
        public static GameManager GetInstance()
        {
            if (_instance == null)
            {
                _instance = new GameManager();
            }
            return _instance;
        }

        public Background background
        { get; set; }
        public MyPlane myPlane
        { get; set; }

        public int Score
        { get; set; }

        public Boolean gameOver = false;

        List<Bullet> bullets = new List<Bullet>();
        public List<EnemyPlane> enemyPlanes = new List<EnemyPlane>();
        public List<Explosion> explosions = new List<Explosion>();
        public void AddSprite(Sprite sprite)
        {
            if (sprite is Background)
            {
                this.background = sprite as Background;
            }
            else if (sprite is MyPlane)
            {
                this.myPlane = sprite as MyPlane;
            }
            else if (sprite is Bullet)
            {
                bullets.Add(sprite as Bullet);
            }
            else if (sprite is EnemyPlane)
            {
                enemyPlanes.Add(sprite as EnemyPlane);
            }
            else if (sprite is Explosion)
            {
                explosions.Add(sprite as Explosion);
            }
        }

        public void RemoveAllSprites()
        {
            enemyPlanes.Clear();
            bullets.Clear();
            explosions.Clear();
        }

        public void RemoveSprite(Sprite sprite)
        {
            if (sprite is EnemyPlane)
            {
                enemyPlanes.Remove(sprite as EnemyPlane);
            }
            else if (sprite is Bullet)
            {
                bullets.Remove(sprite as Bullet);
            }
            else if (sprite is Explosion)
            {
                explosions.Remove(sprite as Explosion);
            }
        }
        public void Draw(Graphics g)
        {
            this.background.Draw(g);
            if (this.myPlane != null)
            {
                this.myPlane.Draw(g);
            }

            for (int i = 0; i < bullets.Count; i++)
            {
                bullets[i].Draw(g);
            }
            for (int i = 0; i < enemyPlanes.Count; i++)
            {
                enemyPlanes[i].Draw(g);
            }
            for (int i = 0; i < explosions.Count; i++)
            {
                explosions[i].Draw(g);
            }
        }

        public void DetectCollision()        //碰撞检测
        {
            #region 判断玩家的子弹是否打到了敌人的身上
            for (int i = 0; i < bullets.Count; i++)
            {
                for (int j = 0; j < enemyPlanes.Count; j++)
                {
                    if (bullets[i].GetRectangle().IntersectsWith      //判断是否相交
                        (enemyPlanes[j].GetRectangle()))
                    {
                        enemyPlanes[j].Life -= bullets[i].Power;
                        enemyPlanes[j].IsOver();
                        bullets.Remove(bullets[i]);
                        break;

                    }
                }
            }
            #endregion

            #region 判断我方飞机与敌方飞机是否碰撞
            for (int i = 0; i < enemyPlanes.Count; i++)
            {
                if (this.myPlane != null && enemyPlanes[i].GetRectangle().IntersectsWith(this.myPlane.GetRectangle()))
                {
                    myPlane.Life = 0;
                    myPlane.IsOver();
                    break;
                }
            }

            #endregion
        }

    }
}
