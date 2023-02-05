using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;

namespace PlaneGame
{
    abstract class BasePlane : Sprite
    {
        private Image image;
        public BasePlane(int x, int y, Image img, int speed, int life, Direction dir) :
            base(x, y, img.Width, img.Height, speed, life, dir)
        {
            this.image = img;
        }
        public abstract void IsOver();
    }
}
