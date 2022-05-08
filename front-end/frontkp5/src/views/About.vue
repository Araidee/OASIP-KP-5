<script setup>
import Araideeicon from '../components/icons/araideeicon.vue';
</script>
 
<template>
    <div>
        <h1 class='text-3xl font-bold'>About Us</h1><br>
        <div>
           <h1 class="topic font-bold">OUR TEAM</h1></div>
           <div>
           <Araideeicon alt="horizon" style="width: 10%;height: 10%" class="profileimg horizon"/>
           <Araideeicon alt="exist" style="width: 10%;height: 10%" class="profileimg exist"/>
           <Araideeicon alt="jubby" style="width: 10%;height: 10%" class="profileimg jubby"/>
        </div>
    </div>
 <!-- <div class="radial-progress bg-primary text-primary-content border-4 border-primary" style="--value:50;">50%</div> -->
</template>
 
<style>
.topic{
    text-align: center;
    font-size: 50pt;
}
.horizon{
float: left;
}
.exist{
float: initial;
}
.jubby{
float: right;
}
.profileimg{   
  border-radius: 50%;
}
</style>