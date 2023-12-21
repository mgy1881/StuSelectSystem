import {defineStore} from 'pinia'
import {ref} from 'vue'
const useUserInfoStore = defineStore('userInfo',()=>{
    //定义状态相关的内容

    const info = ref({})
    const type = ref('')

    const setInfo = (newInfo)=>{
        info.value = newInfo
    }


    const removeInfo = ()=>{
        info.value = {}
    }

    const setType = (tp)=>{
        type.value = tp;
    }

    const removeType = ()=>{
        type.value = '';
    }

    return {info,setInfo,removeInfo,type,setType,removeType}

},{persist:true})

export default useUserInfoStore;