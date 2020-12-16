<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

    <breadcrumb class="breadcrumb-container" />

    <div class="right-menu">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          更多<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="info" icon="el-icon-plus">个人信息</el-dropdown-item>
          <el-dropdown-item command="exit" icon="el-icon-circle-plus">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-drawer title="我的信息" :visible.sync="drawer" :direction="direction" :before-close="handleClose">
      <el-card>
        <div style="display: flex">
          <div><p style="color: gray; font-size: 14px">登录账号：{{admin.username}}</p></div>
          <div style="margin-left: 30px"><el-button @click="modifyPasswordDialogFormVisible = true" type="primary" icon="el-icon-edit" circle></el-button></div>
        </div>
        <p style="color: gray; font-size: 14px">账号创建时间：{{admin.create_time}}</p>
        <el-button @click="addAdminAccountDialogFormVisible = true" type="info">添加管理员账号</el-button>
      </el-card>
    </el-drawer>

    <el-dialog title="修改密码" :visible.sync="modifyPasswordDialogFormVisible">
      <el-form :model="form">
        <el-form-item label="原密码" :label-width="formLabelWidth">
          <el-input placeholder="原始密码" type="password" v-model="form.oldPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" :label-width="formLabelWidth">
          <el-input placeholder="密码长度为6到10位" type="password" v-model="form.newPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" :label-width="formLabelWidth">
          <el-input placeholder="再次输入密码" type="password" v-model="form.confirmPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyPassword">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="添加账号" :visible.sync="addAdminAccountDialogFormVisible">
      <el-form :model="adminAccountForm">
        <el-form-item label="账号" :label-width="formLabelWidth">
          <el-input placeholder="账号长度只能是6位" v-model="adminAccountForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth">
          <el-input placeholder="6到10位密码长度" type="password" v-model="adminAccountForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" :label-width="formLabelWidth">
          <el-input placeholder="再次输入密码" type="password" v-model="adminAccountForm.confirmPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addAdminAccountDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addAdminAccount">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import Breadcrumb from "@/components/Breadcrumb";
import Hamburger from "@/components/Hamburger";

export default {
  data() {
    return {
      drawer: false,
      direction: 'rtl',
      modifyPasswordDialogFormVisible: false,
      addAdminAccountDialogFormVisible: false,
      form: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: '',
      },
      formLabelWidth: '120px',
      adminAccountForm: {
        username: '',
        password: '',
        confirmPassword: ''
      },
      admin: {}
    }
  },

  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters(["sidebar", "avatar"])
  },
  methods: {

    handleCommand(command) {
      switch(command) {
        case 'info':
          this.drawer = true
          break
        case 'exit':
          this.logout()
          break
      }
    },

    async addAdminAccount() {
      if (this.adminAccountForm.username.trim() === '' || this.adminAccountForm.password.trim() === '' || this.adminAccountForm.confirmPassword.trim() === '') {
        this.$message.error('信息不全！')
        return
      }
      if (this.adminAccountForm.password.trim() !== this.adminAccountForm.confirmPassword.trim()) {
        this.$message.error('两次输入密码不相同！')
        return
      }
      if (this.adminAccountForm.username.trim().length !== 6) {
        this.$message.error('登录账号只能是6位数！')
        return
      }
      if (this.adminAccountForm.password.trim().length >= 6 && this.adminAccountForm.password.trim().length <= 10) {
        try {
          const res = await this.$http.post('/user-server/api/v1/adminAccount/pri/add', {
            username: this.adminAccountForm.username.trim(),
            password: this.adminAccountForm.password.trim()
          })
          if (res.code === 1) {
            this.$message({
              type: 'success',
              message: res.message
            })
          } else {
            this.$message.error(res.message)
          }
        } catch (err) {
          console.log(err)
        } finally {
          this.adminAccountForm.username = ''
          this.adminAccountForm.password = ''
          this.adminAccountForm.confirmPassword = ''
          this.addAdminAccountDialogFormVisible = false
        }
      } else {
        this.$message.error('密码只能是6到10位！')
      }
    },

    async modifyPassword() {
      if (this.form.oldPassword.trim() === '' || this.form.newPassword.trim() === '' || this.form.confirmPassword.trim() === '') {
        this.$message.error('信息不全！')
        return
      }
      if (this.form.newPassword.trim() !== this.form.confirmPassword.trim()) {
        this.$message.error('两次输入密码不相同！')
        return
      }
      if (this.form.newPassword.trim().length < 6 || this.form.newPassword.trim().length > 10) {
        this.$message.error('密码只能6到10位')
        return
      }
      try {
        const res = await this.$http.post('/user-server/api/v1/adminAccount/pri/updatePassword', {
          adminAccount: {
            username: localStorage.getItem('username'),
            password: this.form.oldPassword.trim()
          },
          newPassword: this.form.newPassword.trim()
        })
        console.log(res)
        if (res.code === 1) {
          this.$message({
            type: 'success',
            message: res.message
          })
        } else {
          this.$message.error(res.message)
        }
      } catch (err) {
        console.log(err)
      } finally {
        this.form.newPassword = ''
        this.form.oldPassword = ''
        this.form.confirmPassword = ''
        this.modifyPasswordDialogFormVisible = false
      }
      
    },

    handleClose(done) {
      done()
    },

    toggleSideBar() {
      this.$store.dispatch("app/toggleSideBar");
    },
    async logout() {
      console.log('登出')
      //清空token信息
      this.$store.commit("user/setToken", "");
      this.$store.commit("user/setUsername", "");
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      this.$router.push(`/?redirect=${this.$route.fullPath}`);
    }
  },

  async created() {
    const res = await this.$http.post('/user-server/api/v1/adminAccount/pri/getAdminByUsername', { username: localStorage.getItem('username') })
    if (res.code === 1) this.admin = res.data
  }
};
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background 0.3s;
    -webkit-tap-highlight-color: transparent;

    &:hover {
      background: rgba(0, 0, 0, 0.025);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;
    margin-right: 20px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background 0.3s;

        &:hover {
          background: rgba(0, 0, 0, 0.025);
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
