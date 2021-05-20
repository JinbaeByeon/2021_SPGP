# 2015182013 변진배
## **2021-1 SPGP TermProject** - 스마트폰 게임 프로그래밍 텀 프로젝트

### 게임 소개
- 위로 진행하는 종 스크롤 탄막 슈팅게임
- 시작 시 플레이어 기체를 선택한 후, 일반 몬스터와 보스 몬스터를 잡으며 높은 점수를 얻는 것이 목표
  
---------------------------------------------------------------------------------------------------------------

### 진행 상황

주차 | 내용 | 완성도
:-----: | :-----: | :-----:
1주차 | 리소스 수집 | 80%, 추가로 필요한 부분 계속 수집
2주차 | 프레임워크 개발 | 100%
3주차 | 플레이어 공격 구현 | 100%
4주차 | 플레이어 이동 구현 | 90%, 부드러운 이동으로 변경 예정
5주차 | 적 유닛 이동 및 공격 구현 | 70%, 보스 미완성
6주차 | 아이템 및 충돌처리 구현 | 80%, 폭탄 미완성
7주차 | 시작 UI 구현 | 60%, 캐릭터 선택 미완성
8주차 | 사운드 구현 | 100%
9주차 | 버그 수정 | 진행중

---------------------------------------------------------------------------------------------------------------

### commit log

주차 | 횟수
:-----: | :-----:
1주차 | 
2주차 | 
3주차 | 
4주차 | 
5주차 | 
6주차 | 
7주차 | 
8주차 | 
9주차 | 

---------------------------------------------------------------------------------------------------------------

### MainGame의 game object


#### class 구성 정보


##### Player: 플레이어의 power, hp, 공격속도, 총알속도 정보를 갖고 있는 클래스. 인덱스를 이용해 좌우 이동에 따른 플레이어 기체를 그린다.
- 구성정보: IndexedGameBitmap 사용, 클릭이벤트를 통해 이동한 거리만큼 x,y를 이동하며 x축 이동에 대해서 index값을 조정하여 기체가 기울은 효과를 준다.


##### Enemy: 적 오브젝트의 power, hp, speed, 공격속도, 총알속도 정보를 갖고 있는 클래스. 단일 비트맵 기체를 그린다.
- 구성정보: GameBitmap 사용, 일정 시간마다 x축 이동 방향이 랜덤하게 정해진다.


##### EnemyGenerator: 스폰시간 마다 적 기체들을 생성하는 클래스. 스폰주기는 점점 짧아지며 스폰이 지속될수록 더 강한 적기들을 생성한다.

![image](https://user-images.githubusercontent.com/22373033/119006982-d18b5080-b9cb-11eb-861e-79ddb7d7523b.png)

(스폰주기가 짧아지게 하는 코드)

![image](https://user-images.githubusercontent.com/22373033/119007262-08f9fd00-b9cc-11eb-906e-43e874000e75.png)

(스폰이 됨에 따라 더 강해지도록 하는 코드)



##### Bullet: MainGame의 모든 총알을 담당하는 클래스. 플레이어와 적의 총알은 MainGame의 Layer를 통해 분리되며 Player와 Enemy에서 생성하여 MainGame에 넘겨준다.
- 구성정보: GameBitmap 사용, 생성 시 받는 speed만큼 y 방향으로 이동한다.

![image](https://user-images.githubusercontent.com/22373033/119007703-70b04800-b9cc-11eb-8971-793622073a3c.png)

(충돌한 객체에게 총알이 생성 시 받는 power만큼의 데미지를 적용)



##### Item: 현재 파워업, 폭탄, 체력 아이템으로 나뉘며, 플레이어와 충돌처리 시 아이템 타입을 플레이어에게 넘겨주어 타입에 따른 효과를 적용시킨다. 벽에 닿을 시 튕긴다.

![image](https://user-images.githubusercontent.com/22373033/119008097-d0a6ee80-b9cc-11eb-9a4f-4094ba80bd10.png)

(GameBitmap을 타입에 따라 IndexedAnimationGameBitmap과 GameBitmap으로 사용)



##### Coin: 몬스터 처치 시 아래방향으로 떨어져 내리는 코인. 플레이어와 충돌 시 점수를 획득한다.
- 구성정보: IndexedAnimationGameBitmap을 사용, 아래 방향으로 떨어져내리며 벽에 닿아도 튕기지 않는다.


##### Sound: HashMap을 이용하여 리소스 ID에 따른 사운드 ID를 저장한 뒤, 나중에 저장된 HashMap의 사운드 ID를 이용해 소리를 재생한다. 
![image](https://user-images.githubusercontent.com/22373033/119008835-7b1f1180-b9cd-11eb-8b3c-6e8dd1f7ffdc.png)

(HashMap에 사운드ID 저장하는 코드)

![image](https://user-images.githubusercontent.com/22373033/119008940-97bb4980-b9cd-11eb-8c03-40057376c9b6.png)

(HashMap의 사운드ID를 이용하는 코드)



##### BGSound: singleton을 이용하여 하나의 bgm을 실행 및 일시정지할 수 있는 클래스. 배경음악의 loop 처리를 위해 별도로 만듦.
- 구성정보: 한번만 init()을 통해 raw폴더의 리소스를 저장하면, 이후 start/pause를 통해 bgm을 처리할 수 있다.


##### Health: 플레이어의 체력바 UI를 위한 클래스. 플레이어의 체력을 좌측 상단에 그려준다.
- 구성정보: drawRect를 통해 회색 직사각형 위에 빨간색 직사각형을 그려 체력바를 그린다. setHP(int)함수를 통해 플레이어의 체력과 연동한다.


##### VerticalScrollBackground: 종스크롤 배경을 위한 클래스. 배경 비트맵의 폭을 GameView에 맞춰 화면을 가득채울 때까지 반복하여 그리며 아래로 이동시킨다.




#### 상호작용 정보
![image](https://user-images.githubusercontent.com/22373033/119017805-5713fe00-b9d6-11eb-8d20-a34e21aa1e8b.png)




#### 충돌처리
![image](https://user-images.githubusercontent.com/22373033/119018575-2c767500-b9d7-11eb-937a-5bcd5c498a64.png)


![image](https://user-images.githubusercontent.com/22373033/119018615-3c8e5480-b9d7-11eb-8e83-003dfb8904ba.png)




---------------------------------------------------------------------------------------------------------------
[발표 영상 Youtube Link](https://www.youtube.com/watch?v=cfKxoxutmGk "2021-1 SPGP TermProject")

[이전 README.md Link](https://github.com/JinbaeByeon/2021_SPGP/tree/v1/TermProject#2015182013-변진배 "README.md 1차")








