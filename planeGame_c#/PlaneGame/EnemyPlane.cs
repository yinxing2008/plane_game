using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using PlaneGame.Properties;

namespace PlaneGame
{
    class EnemyPlane : BasePlane
    {
        private static Image image = Resources.enemy1;

        public EnemyPlane(int x, int y) :
            base(x, y, image, 5, 1, Direction.Down)
        {
        }

        public override void Draw(Graphics g)
        {
            this.Move();
            g.DrawImage(image, this.X, this.Y);
        }
        public override void Move()
        {
            this.Y += this.Speed;

            if (this.Y >= 700)
            {
                GameManager.GetInstance().RemoveSprite(this);
            }
        }

        public override void IsOver()
        {
            if (this.Life <= 0)
            {
                //敌人飞机坠毁,应该把敌机从游戏中移除
                GameManager.GetInstance().RemoveSprite(this);
                //播放敌机爆炸的图片
                GameManager.GetInstance().AddSprite(new Explosion(this.X, this.Y));

                GameManager.GetInstance().Score += 1;
            }
        }
    }
}

