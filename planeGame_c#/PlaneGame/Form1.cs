using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.IO;
using System.Text;
using System.Windows.Forms;

namespace PlaneGame
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            StartGame();
        }
        static Random r = new Random();
        /// <summary>
        ///游戏初始化
        /// </summary>
        public void StartGame()
        {
            GameManager.GetInstance().Score = 0;
            //首先需要初始化的是我们的背景
            GameManager.GetInstance().AddSprite(new Background(0, -850, 5));
            //初始化英雄飞机
            GameManager.GetInstance().AddSprite(new MyPlane(100, 100, 5, 3, Sprite.Direction.Up));
        }
        //初始化敌方飞机
        public void InitEnemyPlanes()
        {
            //初始人敌方飞机
            for (int i = 0; i < 5; i++)
            {
                GameManager.GetInstance().AddSprite(new EnemyPlane(r.Next(0, this.Width), -200));
            }
        }
        private void Form1_Load(object sender, EventArgs e)
        {
            //在窗体加载的时候,解决闪烁问题
            this.SetStyle(ControlStyles.OptimizedDoubleBuffer | ControlStyles.ResizeRedraw |
                ControlStyles.AllPaintingInWmPaint, true);
        }

        private void Form1_Paint(object sender, PaintEventArgs e)
        {
            //当窗口重绘时,我们就绘制我们的背景
            GameManager.GetInstance().Draw(e.Graphics);
            string score = GameManager.GetInstance().Score.ToString();
            //绘制玩家的分数
            e.Graphics.DrawString(score, new Font("宋体", 20, FontStyle.Bold),
                Brushes.Red, new Point(0, 0));
        }

        private void timerBG_Tick(object sender, EventArgs e)
        {
            this.Invalidate();     //每50毫秒刷新一次
            //不停的判断敌机的数量
            //获取敌方飞机的数量
            int count = GameManager.GetInstance().enemyPlanes.Count;
            if (count <= 1)
            {
                //再次对敌机进行初始化
                InitEnemyPlanes();
            }
            //不停的进行碰撞检测
            GameManager.GetInstance().DetectCollision();
            if (GameManager.GetInstance().gameOver)
            {
                timerBG.Enabled = false;
                GameManager.GetInstance().RemoveAllSprites();
                DialogResult result = MessageBox.Show("游戏结束", "", MessageBoxButtons.OK, MessageBoxIcon.Information);
                GameManager.GetInstance().gameOver = false;
                StartGame();
                timerBG.Enabled = true;
            }
        }

        private void Form1_MouseMove(object sender, MouseEventArgs e)
        {
            //当鼠标在窗体移动的时候,让飞机跟着鼠标的移动而移动
            if (GameManager.GetInstance().myPlane != null)
            {
                GameManager.GetInstance().myPlane.MouseMove(e);  //调用玩家飞机的mouseMove方法
            }
        }

        private void Form1_MouseDown(object sender, MouseEventArgs e)
        {
            if (GameManager.GetInstance().myPlane != null)
            {
                GameManager.GetInstance().myPlane.Shoot();
            }
        }
    }
}
