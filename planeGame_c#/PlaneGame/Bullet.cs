using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using PlaneGame.Properties;

namespace PlaneGame
{
    class Bullet : Sprite
    {
        private static Image image = Resources.bullet;
        public int Power
        { get; set; }
        public Bullet(BasePlane pf, int speed, int power) :
            base(pf.X + pf.Width / 2 - 20, pf.Y + pf.Height / 2 - 45, image.Width, image.Height, speed, 0, pf.direction)
        {
            this.Power = power;
        }


        public override void Move()
        {
            this.Y -= this.Speed;
            if (this.Y <= 0)
            {
                GameManager.GetInstance().RemoveSprite(this); //在游戏中移除子弹对象
            }
        }

        public override void Draw(Graphics g)
        {
            this.Move();
            g.DrawImage(image, this.X, this.Y, this.Width / 2, this.Height / 2);
        }
    }
}
