using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using PlaneGame.Properties;
namespace PlaneGame
{
    class Explosion : Sprite
    {
        private Image[] img1 =
         {
                    Resources.enemy1_down1,
                    Resources.enemy1_down2,
                    Resources.enemy1_down3,
                    Resources.enemy1_down4
         };

        public Explosion(int x, int y) : base(x, y)
        {

        }

        public override void Draw(Graphics g)
        {
            for (int i = 0; i < img1.Length; i++)
            {
                g.DrawImage(img1[i], this.X, this.Y);
            }
            GameManager.GetInstance().RemoveSprite(this);

        }
    }
}
