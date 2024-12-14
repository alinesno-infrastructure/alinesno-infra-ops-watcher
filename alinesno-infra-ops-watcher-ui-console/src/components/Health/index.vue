<template>

    <div class="headlth-box">
        <div class="circle-wave">
            <div class="circle-wave-bg"></div>
            <div class="circle-wave-inner">
                <div class="circle-wave-value"> 
                    100 
                    <!-- <div class="unit">分</div> -->
                </div>
                <div class="circle-wave-desc">正常</div>
            </div>
        </div>
    </div>

</template>

<script setup>
</script>

<style scoped lang="scss">

$chartWidth: 75px;
$chartColor: #3b5998 ;

.circle-wave {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: $chartWidth;
  height: $chartWidth;

  &:before {
    content: "";
    border-radius: 56% 56% 52% 54%;
    background-color: $chartColor;
    opacity: 0.8;
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    animation: rotate 12s infinite linear;
  }

  &:after {
    content: "";
    border-radius: 44% 44% 42% 46%;
    background-color: $chartColor;
    opacity: 0.6;
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    animation: rotate 10s infinite linear;
  }

  .circle-wave-desc {
    margin-top: 4px;
    font-size: 12px;
  }

  .circle-wave-value {
    font-size: 15px;
    font-weight: 600;
    line-height: 1;
    display: inline-flex;
    align-items: flex-end;

    .unit {
      font-size: 12px;
      margin-left: 4px;
      transform: translateY(-6px);
    }
  }

  .circle-wave-inner {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    z-index: 1;
    width: calc($chartWidth - 10px);
    height: calc($chartWidth - 10px);
    // background: #fff !important;
    border-radius: 60% 60% 60% 60%;
    color: $chartColor;
  }

  .circle-wave-bg {
    border-radius: 46% 46% 42% 44%;
    background-color: $chartColor;
    opacity: 0.8;
    display: block;
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    animation: rotate 8s linear infinite;
  }
}

.headlth-box {
    text-align: center;
    margin: auto;
    // width: 200px;
    // margin-top: 10px;
    align-items: center;
    display: inline-flex;
    flex: 1 1 0;
    justify-content: center;
    overflow: hidden;
}

</style>


