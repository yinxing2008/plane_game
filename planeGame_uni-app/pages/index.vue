//厦门大学计算机专业 | 前华为工程师
//专注《零基础学编程系列》  http://lblbc.cn/blog
//包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
//公众号：蓝不蓝编程
<template>
	<view class="content" id="game">
		<view class="score" >得分：{{score}}</view>
		<!-- 敌机 -->
		<template v-if="isStart">
			<Enemy v-for="(enemy, index) in enemyData" :data="enemy" :ref="enemy.id" :key="enemy.id"
				:bulletData="bulletData" :params="config" @remove="removeEnemy" @removeBullet="removeBullet" />
		</template>

		<!-- 我方飞机 -->
		<template v-if="isStart">
			<Plane v-for="plane in planeData" :data="plane" :enemyData="enemyData" :params="config" :key="plane.id"
				@gameOver="gameOver" @addBullet="addBullet" />
		</template>

		<!-- 子弹 -->
		<template v-if="isStart">
			<Bullet v-for="bullet in bulletData" :key="bullet.id" :data="bullet" @removeBullet="removeBullet" />
		</template>

		<!-- 起始界面 -->
		<view class="game-start-container" v-if="!isStart">
			<view class="btn" @click="startGame">开始</view>
		</view>

		<!-- 游戏结束 -->
		<view class="game-start-container" v-if="isGameOver">
			<view class="btn" @click="startGame">重新开始</view>
		</view>
	</view>
</template>

<script>
	import Enemy from '../pages/enemy.vue';
	import Plane from '../pages/plane.vue';
	import Bullet from '../pages/bullet.vue';

	import { deleteModel } from '../utils/utils.js';
	export default {
		data() {
			return {
				title: 'Hello',
				isStart: false,
				score: 0,
				isGameOver: false,
				planeData: [],
				enemyData: [],
				bulletData: [],
				enemyTimer: null,
				config: {
					winWdith: 0,
					winHeight: 0
				},
			};
		},
		components: {
			Enemy,
			Plane,
			Bullet
		},
		onLoad() {
			this.init();
		},
		methods: {
			init() {
				this.isStart = false;
				this.isGameOver = false;
			},
			startGame() {
				this.isStart = true;
				this.isGameOver = false;
				this.score = 0;

				this.initScreen();
				// 初始化敌机
				this.initEnemy();
				// 初始化我方飞机
				this.initPlane();
			},
			initScreen() {
				this.config.winWdith = document.getElementById('game').offsetWidth;
				this.config.winHeight = document.getElementById('game').offsetHeight;
			},
			initEnemy() {
				// 创建飞机参数
				const createEnemyParam = () => {
					const x = (this.config.winWdith - 59) * Math.random();

					return {
						x,
						y: -36,
						width: 59,
						height: 36,
						screenHeight: this.config.winHeight,
						id: `enemy` + new Date().getTime(),
						isExplosion: false
					};
				};

				// 创建敌机
				const createEnemy = () => {
					const param = createEnemyParam();
					this.enemyData.push(param);
				};

				createEnemy();
				this.enemyTimer = setInterval(createEnemy, 1500);
			},
			removeEnemy(id) {
				deleteModel(this.enemyData, id);
				this.score++;
			},
			gameOver() {
				this.isGameOver = true;
				this.planeData = [];
				this.enemyData = [];
				this.bulletData = [];
				clearInterval(this.enemyTimer);
			},
			addBullet(param) {
				this.bulletData.push(param);
			},
			removeBullet(id) {
				deleteModel(this.bulletData, id);
			},
			initPlane() {
				const data = {
					width: 98,
					height: 122,
					x: (this.config.winWdith - 98) / 2,
					y: this.config.winHeight - 122,
					id: `plane` + new Date().getTime()
				};

				this.planeData.push(data);
			}
		}
	};
</script>

<style scoped>
	.score{
		margin-left: 20px;
		margin-top: 20px;
	}
	.content {
		position: absolute;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		background-image: url('@/static/images/bg.jpg');
		background-repeat: no-repeat;
		background-size: 100%;
	}

	.game-start-container {
		width: 20rem;
		height: 74%;
		position: fixed;
		top: 6rem;
		left: 50%;
		transform: translateX(-50%);
	}

	.logo {
		width: 100%;
	}

	.btn {
		bottom: 1rem;
		border: 3px solid #3a3939;
		width: 70%;
		height: 3rem;
		line-height: 3rem;
		left: 50%;
		transform: translateX(-50%);
		cursor: pointer;
		border-radius: 3rem;
		text-align: center;
		position: absolute;
	}
</style>
