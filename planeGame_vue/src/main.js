import App from './App.vue'
import { getContainer } from './game'
import { createApp } from './runtime-canvas'

console.warn = () => { };
createApp(App).mount(getContainer())