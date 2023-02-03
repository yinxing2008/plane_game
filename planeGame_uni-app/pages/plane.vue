//厦门大学计算机专业 | 前华为工程师
//专注《零基础学编程系列》  http://lblbc.cn/blog
//包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
//公众号：蓝不蓝编程
<template>
	<view class="plane" :class="getClass" :style="{ left: data.x + 'px', top: data.y + 'px' }"></view>
</template>

<script>
	import { isCollision } from '../utils/utils.js';
	export default {
		props: {
			isExplosion: {
				type: Boolean,
				default: false
			},
			enemyData: {
				type: Array,
				default: () => []
			},
			params: {
				type: Object,
				default: () => {
					return {};
				}
			},
			data: {
				type: Object,
				default: () => {
					return {};
				}
			}
		},
		data() {
			return {
				keyTop: false,
				keyRight: false,
				keyBottom: false,
				keyLeft: false,
				moveTimer: null,
				shootTimer: 0
			};
		},
		computed: {
			getClass() {
				const classStyle = [`plane`];
				const explosion = {};
				explosion[`plane_animate`] = this.isExplosion;
				classStyle.push(explosion);
				return classStyle;
			}
		},
		methods: {
			init() {
				// 初始化坐标
				this.initPosition();
				// 初始化操作
				this.initEvent();
				// 初始化
				this.initMove();
				this.shootTimer = setInterval(() => {
					this.shoot()
				}, 200)
			},
			initPosition() {
				this.keyTop = false;
				this.keyRight = false;
				this.keyBottom = false;
				this.keyLeft = false;
			},
			initEvent() {
				document.addEventListener('keydown', e => {
					switch (e.keyCode) {
						case 38: //up键
							this.keyTop = true;
							break;
						case 40: //down键
							this.keyBottom = true;
							break;
						case 37: //left键
							this.keyLeft = true;
							break;
						case 39: //right键
							this.keyRight = true;
							break;
					}
				});
				document.addEventListener('keyup', e => {
					switch (e.keyCode) {
						case 38: //up键
							this.keyTop = false;
							break;
						case 40: //down键
							this.keyBottom = false;
							break;
						case 37: //left键
							this.keyLeft = false;
							break;
						case 39: //right键
							this.keyRight = false;
							break;
					}
				});
			},
			move() {
				if (this.keyTop && this.data.y > 0) {
					this.data.y -= 5;
				}
				if (this.keyBottom && this.data.y + 122 < this.params.winHeight) {
					this.data.y += 5;
				}
				if (this.keyLeft && this.data.x > 0) {
					this.data.x -= 5;
				}
				if (this.keyRight && this.data.x + 98 < this.params.winWdith) {
					this.data.x += 5;
				}
			},
			initMove() {
				this.moveTimer = () => {
					this.move();
					// 碰撞检测
					this.detectCollision();
					requestAnimationFrame(this.moveTimer);
				};
				this.moveTimer();
			},
			shoot() {
				const params = {
					width: 7,
					height: 18,
					x: this.data.x + 49,
					y: this.data.y,
					id: `bullet` + new Date().getTime()
				};
				this.$emit('addBullet', params);
			},
			detectCollision() {
				if (this.enemyData.length === 0) return;

				this.enemyData.forEach(el => {
					const collision = isCollision(el, this.data);
					if (collision) {
						this.$emit('gameOver'); //游戏结束
					}
				});
			}
		},
		mounted() {
			this.init();
		},
		unmounted() {
			clearInterval(this.shootTimer)
		}
	};
</script>

<style scoped>
	.plane {
		position: fixed;
		width: 98px;
		height: 122px;
		z-index: 3;
		background: url(@/static/images/my_plane.png) no-repeat left top;
	}

	.plane_animate {
		animation: plane_animate 0.5s steps(5) both infinite;
		-webkit-animation: plane_animate 0.5s steps(5) both infinite;
	}

	/* 飞机-爆炸效果 */
	@keyframes plane_animate {
		0% {
			background-position: 0 0;
		}

		100% {
			background-position: -490px 0;
		}
	}

	@-webkit-keyframes plane_animate {
		0% {
			background-position: 0 0;
		}

		100% {
			background-position: -490px 0;
		}
	}
</style>
