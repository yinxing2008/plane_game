using System;
using System.Collections.Generic;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using PlaneGame.Properties;

namespace PlaneGame
{
    class MyPlane : BasePlane
    {
        private static Image imgPlane = Resources.my_plane;
        public MyPlane(int x, int y,  int speed, int life, Direction dir) : base(x, y, imgPlane, speed, life, dir)
        {
        }

        public override void Draw(Graphics g)
        {
            g.DrawImage(imgPlane, this.X, this.Y,this.Width/2,this.Height/2);
        }

        //让玩家飞机跟 着鼠标走
        public void MouseMove(MouseEventArgs e)
        {
            this.X = e.X;
            this.Y = e.Y;
        }
        //玩家飞机发射子弹的方法
        public void Shoot()
        {
            //初始化我们玩家子弹到游戏中
            GameManager.GetInstance().AddSprite(new Bullet(this, 10, 1));
        }

        public override void IsOver()
        {
            if (this.Life <= 0)
            {
                GameManager.GetInstance().gameOver = true;
            }
        }
    }
}
