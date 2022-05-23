<script setup>
import Araideeicon from "../components/icons/araideeicon.vue";
import non from "../components/icons/non.vue";
import kobfha from "../components/icons/kobfha.vue";
import jubby from "../components/icons/jubby.vue";
</script>

<template>
  <!-- <div>
        <h1 class='text-3xl font-bold'>About Us</h1><br>
        <div>
           <h1 class="topic font-bold">OUR TEAM</h1></div>
           <div>
           <Araideeicon alt="horizon" style="width: 10%;height: 10%" class="profileimg horizon"/>
           <Araideeicon alt="exist" style="width: 10%;height: 10%" class="profileimg exist"/>
           <Araideeicon alt="jubby" style="width: 10%;height: 10%" class="profileimg jubby"/>
        </div>
    </div>
 <div class="radial-progress bg-primary text-primary-content border-4 border-primary" style="--value:50;">50%</div> -->
  <link
    rel="stylesheet"
    href="https://use.fontawesome.com/releases/v6.1.1/css/all.css"
  />

  <div class="wrapper">
    <h1>Our team</h1>
    <div class="our_team">
      <div class="team_member">
        <div class="member_img">
          <div class="social_media">
            <div class="item">
              <a href="https://github.com/sarinweerakun" target="_blank"
                ><i class="fa-brands fa-github"></i
              ></a>
            </div>
            <div class="item">
              <a href="https://www.instagram.com/imhorizonn_/" target="_blank"
                ><i class="fa-brands fa-instagram"></i
              ></a>
            </div>
            <div class="item">
              <a href="https://www.facebook.com/kobfhasarin" target="_blank"
                ><i class="fa-brands fa-facebook"></i
              ></a>
            </div>
          </div>
          <kobfha />
        </div>
        <h3>Sarin<br />Weerakun</h3>
        <span>63130500115</span>
      </div>
      <div class="team_member">
        <div class="member_img">
          <div class="social_media">
            <div class="item">
              <a href="https://github.com/Navisk555" target="_blank"
                ><i class="fa-brands fa-github"></i
              ></a>
            </div>
            <div class="item">
              <a href="https://www.instagram.com/nonmvk_/" target="_blank"
                ><i class="fa-brands fa-instagram"></i
              ></a>
            </div>
            <div class="item">
              <a href="https://www.facebook.com/excister" target="_blank"
                ><i class="fa-brands fa-facebook"></i
              ></a>
            </div>
          </div>
          <non />
        </div>
        <h3>Darun Mahavanakul</h3>
        <span>63130500141</span>
      </div>
      <div class="team_member">
        <div class="member_img">
          <div class="social_media">
            <div class="item">
              <a href="https://github.com/jubbyr" target="_blank"
                ><i class="fa-brands fa-github"></i
              ></a>
            </div>
            <div class="item">
              <a href="https://www.instagram.com/jubby_nr/" target="_blank"
                ><i class="fa-brands fa-instagram"></i
              ></a>
            </div>
            <div class="item">
              <a
                href="https://www.facebook.com/udomchai.nirasrop"
                target="_blank"
                ><i class="fa-brands fa-facebook"></i
              ></a>
            </div>
          </div>
          <jubby />
        </div>
        <h3>Udomchai Nirasrop</h3>
        <span>63130500161</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.wrapper {
  margin-top: 60px;
  text-align: center;
}

.wrapper h1 {
  font-size: 48px;
  margin-bottom: 25px;
}

.wrapper .our_team {
  display: flex;
  width: auto;
  justify-content: center;
}

.wrapper .our_team .team_member {
  width: 250px;

  margin: 5px;
  background: white;
  padding: 20px 10px;
}

.wrapper .our_team .team_member .member_img {
  max-width: 190px;
  width: 100%;
  height: 190px;
  margin: 0 auto;
  border-radius: 50%;
  padding: 5px;
  cursor: pointer;
  position: relative;
}

.wrapper .our_team .team_member img {
  width: 100%;
  height: 100%;
}

.wrapper .our_team .team_member h3 {
  text-transform: uppercase;
  font-size: 18px;
  line-height: 18px;
  letter-spacing: 2px;
  margin: 15px 0 0;
}

.wrapper .our_team .team_member span {
  font-size: 18px;
}

.wrapper .our_team .team_member p {
  margin-top: 20px;
  font-size: 14px;
  line-height: 20px;
}

.wrapper .our_team .team_member .social_media {
  position: absolute;
  top: 5px;
  left: 5px;
  width: 95%;
  height: 95%;
  background: rgba(0, 0, 0, 0.65);
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  transform: scale(0);
  transition: all 0.5s ease;
}

.wrapper .our_team .team_member .social_media .item {
  margin: 0 10px;
}

.wrapper .our_team .team_member .social_media .item .fa-brands {
  color: #8c7ae6;
  font-size: 20px;
}

.wrapper .our_team .team_member .member_img:hover .social_media {
  transform: scale(1);
}
</style>
