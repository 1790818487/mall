<template>
    <div class="LoginPage">

      <el-form ref="login" :model="formLogin" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formLogin.username" placeholder="账号"></el-input>
        </el-form-item>

      </el-form>

      {{userList}}

    </div>
</template>

<script src="./_LoginPage.js" lang="js"></script>
<style src="./_LoginPage.css" lang="css" scoped></style>
