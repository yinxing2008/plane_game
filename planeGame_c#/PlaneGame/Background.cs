using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using PlaneGame.Properties;

namespace PlaneGame
{
    class Background : Sprite
    {
        private static Image image = Resources.background;

        public Background(int x, int y, int speed) : base(x, y, image.Width, image.Height, speed, 0, Direction.Down)
        { }
        public override void Draw(Graphics g)
        {
            this.Y += this.Speed;
            if (this.Y == 0)
            {
                this.Y = -850;
            }
            g.DrawImage(image, this.X, this.Y);
        }
    }
}
