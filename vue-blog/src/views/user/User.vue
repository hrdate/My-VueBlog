<template>
  <div>
    <div class="user-banner banner">
      <h1 class="banner-title">个人中心</h1>
    </div>
    <v-card class="blog-container">
      <div>
        <span class="info-title">基本信息</span>
      </div>
      <v-row class="info-wrapper">
        <v-col md="3" cols="12">
          <button id="pick-avatar">
            <v-avatar size="140">
              <img :src="this.$store.state.avatar" />
            </v-avatar>
          </button>
          <avatar-cropper
            @uploaded="uploadAvatar"
            trigger="#pick-avatar"
            upload-url="/user/avatar"
          />
        </v-col>
        <v-col md="7" cols="12">
          <v-text-field
            v-model="userInfo.userName"
            label="昵称"
            placeholder="请输入您的昵称"
          />
          <v-text-field
            v-model="userInfo.webSite"
            class="mt-7"
            label="个人网站"
            placeholder="http://你的网址"
          />
          <v-text-field
            v-model="userInfo.introduce"
            class="mt-7"
            label="简介"
            placeholder="介绍下自己吧"
          />
          <div v-if="loginType != 0" class="mt-7 binding">
            <v-text-field
              disabled
              v-model="email"
              label="邮箱号"
              placeholder="请绑定邮箱"
            />
            <v-btn v-if="email" text 
              disabled
              small @click="openEmailModel">
              修改绑定
            </v-btn>
            <v-btn v-else text small @click="openEmailModel">
              绑定邮箱
            </v-btn>
          </div>
          <v-btn @click="updataUserInfo" outlined class="mt-5">修改</v-btn>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script>
import AvatarCropper from "vue-avatar-cropper";
export default {
  components: { AvatarCropper },
  data: function() {
    return {
      userInfo: {
        email: this.$store.state.email,
        userId: this.$store.state.userId,
        userName: this.$store.state.userName,
        introduce: this.$store.state.introduce,
        webSite: this.$store.state.webSite,
      }
    };
  },
  methods: {
    updataUserInfo() {
      // console.log(this.userInfo)
      this.axios.post("/user/editInfo", this.userInfo).then(({ data }) => {
        // console.log(data)
        if (data.code == 200) {
          this.$store.commit("updateUserInfo", this.userInfo);
          this.$toast({ type: "success", message: data.msg });
        } else {
          this.$toast({ type: "error", message: data.msg });
        }
      });
    },
    uploadAvatar(data) {
      if (data.flag) {
        this.$store.commit("updateAvatar", data.data);
        this.$toast({ type: "success", message: data.message });
      } else {
        this.$toast({ type: "error", message: data.message });
      }
    },
    openEmailModel() {
      this.$store.state.emailFlag = true;
    }
  },
  computed: {
    userId(){
      return this.$store.state.userId
    },
    email() {
      return this.$store.state.email;
    },
    loginType() {
      return this.$store.state.loginType;
    }
  }
};
</script>

<style scoped>
.user-banner {
  background: url(../../assets/img/bokeyuan_1.jpg) center
    center / cover no-repeat;
  background-color: #49b1f5;
}
.info-title {
  font-size: 1.25rem;
  font-weight: bold;
}
.info-wrapper {
  margin-top: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
}
#pick-avatar {
  outline: none;
}
.binding {
  display: flex;
  align-items: center;
}
</style>
