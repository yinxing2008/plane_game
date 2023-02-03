//厦门大学计算机专业 | 前华为工程师
//专注《零基础学编程系列》  http://lblbc.cn/blog
//包含：Java | 安卓 | 前端 | Flutter | iOS | 小程序 | 鸿蒙
//公众号：蓝不蓝编程
<template>
	<view class="bullet" :style="{ left: data.x + 'px', top: data.y + 'px' }"></view>
</template>

<script>
export default {
	props: {
		data: {
			type: Object,
			default: () => {
				return {};
			}
		}
	},
	data() {
		return {
			moveTimer: null,
			x: 0,
			y: 0
		};
	},
	methods: {
		init() {
			this.moveTimer = () => {
				//敌机移动
				this.move();
				// this.setNewPoint();
				// this.moveTimer();
				// 重绘，无限循环
				requestAnimationFrame(this.moveTimer);
			};
			this.moveTimer();
		},
		move() {
			if (this.data.y > 0) {
				// 子弹的加速度
				this.data.y += -5;
			} else {
				// this.isExplosion = true;
				this.remove();
			}
		},
		remove() {
			this.$emit('removeBullet', this.data.id);
		}
	},
	mounted() {
		this.init();
	}
};
</script>

<style scoped>
.bullet {
	width: 7px;
	height: 18px;
	position: fixed;
	z-index: 2;
	background: url(@/static/images/bullet.png) no-repeat left top;
}

.bullet_effect {
	display: none;
}
</style>
