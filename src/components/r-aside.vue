<template>
  <div>
    <el-aside width="200px" style="background-color: rgb(238, 241, 246); height: 810px; border: 1px solid #eee">
      <el-menu router :default-active="$route.path">
        <div v-for="(item, index) in $router.options.routes" :index="index + ''" :key="index">
          <div v-if="item.name === type">
            <div v-for="(item2, index2) in item.children">
              <el-menu-item index="/article/manage">
                    <el-icon>
                        <Promotion />
                    </el-icon>
                    <span>文章管理</span>
              </el-menu-item>
              <el-sub-menu index="item2.path">
                <template #title>
                  <!-- <el-icon>
                            <UserFilled />
                        </el-icon> -->
                  <span>{{ item2.name }}</span>
                </template>
                <!-- <template slot="title"><i class="el-icon-menu"></i>{{ item2.name }}共跟谁</template> -->
                <div v-for="(item3, index3) in item2.children">
                  <el-menu-item index="item3.path">
                    <el-icon>
                      <User />
                    </el-icon>
                    <span>{{ item3.name
                    }}</span>
                  </el-menu-item>
                  <!-- <el-menu-item index="item3.path" :key="item3.path"><i class="el-icon-s-promotion"></i>{{ item3.name
                  }}</el-menu-item> -->
                </div>
              </el-sub-menu>
            </div>
          </div>
        </div>
      </el-menu>
    </el-aside>
  </div>
</template>

<script setup>
import useUserInfoStore from '@/stores/userInfo.js'
const userInfoStore = useUserInfoStore();
const type = userInfoStore.type
</script>

<style scoped></style>