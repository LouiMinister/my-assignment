#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

typedef char bool;

#define FALSE   0
#define TRUE    1

#define STUDY_AREA_BRANCH_SIZE 6
#define STUDY_AREA_SIZE 5
#define RESERVATION_SIZE 10000
#define CUSTOMERID_SIZE 10
#define STARTAT_SIZE 6


typedef struct StudyArea {    
    int id; // 1~5
    int capacity; // 1~10
}StudyArea;

typedef struct StudyAreaBranch {    
    int id; // 1~6
    struct StudyArea * studyArea[STUDY_AREA_SIZE]; // 1~5
}StudyAreaBranch;

typedef struct Reservation {
    char * customerId; // 영문자 및 숫자 조합 5~10글자
    int studyAreaBranchId;
    int studyAreaId;
    int customerCnt; // 사용 인원
    char * startAt; // YYMMDD
    int startHour; // 8~22
    int hours;
    long hashcode;
}Reservation;

typedef struct BranchManager {
    struct Reservation * reservation[RESERVATION_SIZE];
    char currentCustomerId[11];
    struct StudyAreaBranch * studyAreaBranch[STUDY_AREA_BRANCH_SIZE];
}BranchManager;


void initialization(BranchManager ** branchManager);
void loadBranches(BranchManager ** branchManager);
void saveBranches(BranchManager ** branchManager);
void loadReservations(BranchManager ** branchManager);
void saveReservations(BranchManager ** branchManager);

bool isAfter(char * startAt, int startHour);
bool checkStudyAreaBranchExist(BranchManager ** branchManager, int id);
bool checkReservationAlreadyExist(BranchManager ** branchManager, int branchId, int areaId);
bool checkReservationAlreadyExistInBranch(BranchManager ** branchManager, int id);
void branchCreate(BranchManager ** branchManager, int id);
void branchDelete(BranchManager ** branchManager, int id);
bool branchModify(BranchManager ** branchManager, int id);

void manageStudyArea(BranchManager ** branchManager, int branchId);

void addStudyArea(BranchManager ** branchManager, int branchId, int studyAreaId, int capacity);
void deleteStudyArea(BranchManager ** branchManager, int branchId, int studyAreaId);
void updateStudyAreaCapacity(BranchManager ** branchManager, int branchId, int studyAreaId , int capacity);

void setCurrentCustomer(BranchManager ** branchManager, char * id);

void doAdminAction(BranchManager ** branchManager);
void doUserAction(BranchManager ** branchManager);
void lookUpBranches(BranchManager ** branchManager);
void printBranchesTable(BranchManager ** branchManager);
void printStudyAreaTable(BranchManager ** branchManager, int branchId);

bool checkNewReservationOverlap(BranchManager ** branchManager, char * startAt, int startAtTime, int hours, int branchId, int areaId);
void branchCreateReservation(BranchManager ** branchManager, int branchId, int studyAreaId, char * startAtStr, int startAtTime, int duringHours, int customerCnt);
void createReservation(BranchManager ** branchManager);

void lookUpMyReservations(BranchManager ** branchManager);

void modifyMyReservation(BranchManager ** branchManager, long reservationCode, int customerCnt);

void modifyReservation(BranchManager ** branchManager);
void deleteReservation(BranchManager ** branchManager);
void deleteMyReservation(BranchManager ** branchManager, long hashcode);
long inputLong(const char * msg, long start, long end);
int inputUInt(const char * msg, int start, int end);
char * inputChar(const char * msg, const char * regex, int start, int end);
void itoa(int n, char s[]);
void reverse(char []);

void reverse(char s[]){
    int i, j;
    char c;

    for (i = 0, j = strlen(s)-1; i<j; i++, j--) {
        c = s[i];
        s[i] = s[j];
        s[j] = c;
    }
}

void itoa(int n, char s[]){
    int i, sign;

    if ((sign = n) < 0)  /* record sign */
        n = -n;          /* make n positive */
    i = 0;
    do {       /* generate digits in reverse order */
        s[i++] = n % 10 + '0';   /* get next digit */
    } while ((n /= 10) > 0);     /* delete it */
    if (sign < 0)
        s[i++] = '-';
    s[i] = '\0';
    reverse(s);
}

int main(){
    BranchManager * branchManager = NULL;
    int action;
    
    initialization(&branchManager);

     while(1){
        action = inputUInt("모드 선택: 1.관리자모드 2.사용자모드 3.프로그램종료 : ", 1, 3);
        switch (action) {
            case 1:
                doAdminAction(&branchManager);
                break;
            case 2:
                doUserAction(&branchManager);
                break;
            case 3:
                return 0;
        }
     }
    return 0;
}

void initialization(BranchManager ** branchManager){
    int i;
    *branchManager = (BranchManager *)malloc(sizeof(BranchManager));

    for(i = 0; i < STUDY_AREA_BRANCH_SIZE; ++i){
        (*branchManager) ->studyAreaBranch[i] = NULL;
    }

    for(i = 0; i < RESERVATION_SIZE; ++i){
        (*branchManager) ->reservation[i] = NULL;
    }
    loadBranches(branchManager);
    loadReservations(branchManager);
}

void loadBranches(BranchManager ** branchManager){
    const char * filePath = "branches.txt";
    char buffer[1000];
    char * token; 
    int i = 0;
    
    FILE* fp = fopen(filePath, "r");
    if (fp == NULL) {
        // printf("fail to read file");
        return;
    }

    while (!feof(fp)) {
        fgets(buffer, 1000, fp); 
        token = strtok(buffer, " ");  
        if( strstr(token, "StudyAreaBranch") != NULL ){

            int j;
            (*branchManager)->studyAreaBranch[i] = (StudyAreaBranch *)malloc(sizeof(StudyAreaBranch));
            (*branchManager)->studyAreaBranch[i]->id = i;
            

            for(j = 0; j < STUDY_AREA_SIZE; ++j) {
                fgets(buffer, 1000, fp); 
                
                if( strstr(buffer, "null") == NULL ){
                    
                    (*branchManager)->studyAreaBranch[i]->studyArea[j] = (StudyArea *)malloc(sizeof(StudyArea));
                    
                    sscanf(buffer, "%d %d ", &((*branchManager) -> studyAreaBranch[i]->studyArea[j]->id),
                        &((*branchManager) -> studyAreaBranch[i] -> studyArea[j] -> capacity));
                }else{
                    (*branchManager)->studyAreaBranch[i]->studyArea[j] = NULL;
                }
            }
        }
        ++i;
    }
    fclose(fp); 
}

void saveBranches(BranchManager ** branchManager){
    int i, j;
    const char * filePath = "branches.txt";
    FILE* fp = fopen(filePath, "w");
    for(i = 0; i < STUDY_AREA_BRANCH_SIZE; ++i){
        if((*branchManager)->studyAreaBranch[i] == NULL){
            fprintf(fp, "null\n");
        }else{
            fprintf(fp, "StudyAreaBranch\n");
            for(j = 0; j < STUDY_AREA_SIZE; ++j){
                if((*branchManager) -> studyAreaBranch[i] ->studyArea[j] == NULL){
                    fprintf(fp, "null\n");
                }else{
                    fprintf(fp, "%d %d\n", (*branchManager) -> studyAreaBranch[i] -> studyArea[j] -> id
                    , (*branchManager) -> studyAreaBranch[i] -> studyArea[j] -> capacity);
                }
            }
        }
    }
    fclose(fp); 
}

void loadReservations(BranchManager ** branchManager) {
    const char * filePath = "reservations.txt";
    char buffer[1000];
    char customerIdBuffer[CUSTOMERID_SIZE+1];
    char * token; 
    int i = 0;

     FILE* fp = fopen(filePath, "r");
    if (fp == NULL) {
        // printf("fail to read file");
        return;
    }

    while (!feof(fp)) {
        if(i > RESERVATION_SIZE){
            printf("reservation overflow occur!");
            break;
        }

        if(fgets(buffer, 1000, fp) == NULL)
            break;
        (*branchManager) -> reservation[i] = (Reservation *)malloc(sizeof(Reservation));

        (*branchManager) -> reservation[i] -> startAt = (char *)malloc(STARTAT_SIZE + 1);
        
        sscanf(buffer, "%s %d %d %d %s %d %d %ld", customerIdBuffer,
         &((*branchManager) ->reservation[i] -> studyAreaBranchId), 
         &((*branchManager) ->reservation[i] -> studyAreaId), 
         &((*branchManager) ->reservation[i] -> customerCnt), 
         (*branchManager) ->reservation[i] -> startAt, 
         &((*branchManager) ->reservation[i] -> startHour),
         &((*branchManager) ->reservation[i] -> hours),
         &((*branchManager) ->reservation[i] ->hashcode));
         (*branchManager) ->reservation[i] -> customerId = (char *)malloc(strlen(customerIdBuffer) +1);
         strcpy((*branchManager) -> reservation[i] -> customerId, customerIdBuffer);
         
        

        ++i;
    }
    fclose(fp); 
}

void saveReservations(BranchManager ** branchManager) {
    const char * filePath = "reservations.txt";
    char buffer[1000];
    char customerIdBuffer[CUSTOMERID_SIZE+1];
    char * token; 
    int i = 0;

    FILE* fp = fopen(filePath, "w");
    while (TRUE)
    {
        Reservation * reservation = (*branchManager) -> reservation[i];
        if(reservation == NULL)
            break;
        fprintf(fp, "%s %d %d %d %s %d %d %ld\n", reservation->customerId, 
        reservation -> studyAreaBranchId, 
        reservation -> studyAreaId,
        reservation -> customerCnt, 
        reservation -> startAt, 
        reservation -> startHour,
        reservation -> hours,
        reservation -> hashcode 
        );
        ++i;
    }
    fclose(fp); 
}

int inputUInt(const char * msg, int start, int end) {
   int num;
   while(1){
      printf(msg);
      scanf("%d",&num);
      if(getchar()=='\n'){
         if(num >= start && num <= end)
            break;
         continue;
      }
       while(getchar()!='\n');
   }
   return num;
}

long inputLong(const char * msg, long start, long end) {
   long num;
   while(1){
      printf(msg);
      scanf("%ld",&num);
      if(getchar()=='\n'){
         if(num >= start && num <= end)
            break;
         continue;
      }
       while(getchar()!='\n');
   }
   return num;
}

char * inputChar(const char * msg, const char * regex, int start, int end){
    char buffer[1000];
    while(1){
        printf(msg);
        scanf("%s",buffer);
        if(getchar()== '\n'){
            if(strlen(buffer)>=start && strlen(buffer) <= end)
                break;
            continue;
        }
        while(getchar()!='\n');
    }
    return buffer;
}

bool isAfter(char * startAt, int startHour){
    char dest[30];
    strcpy(dest, startAt);
    char startHourBuffer[20];
    itoa(startHour, startHourBuffer);
    int startTime = atoi(strcat(dest, startHourBuffer));
    char yearBuffer[20];
    char monthBuffer[20];
    char dayBuffer[20];
    char hourBuffer[20];
    int now;
    time_t t = time(NULL);
    struct tm * lt = localtime(&t);
    itoa(1900 + lt->tm_year, yearBuffer);
    itoa(1900 + lt->tm_mon +1, monthBuffer);
    itoa(1900 + lt->tm_mday, dayBuffer);
    itoa(1900 + lt->tm_hour, hourBuffer);
    now = atoi(strcat(strcat(strcat(yearBuffer, monthBuffer), dayBuffer), hourBuffer));
    return (startTime >= now);
}

bool checkStudyAreaBranchExist(BranchManager ** branchManager, int id){
    if((*branchManager) -> studyAreaBranch[ id ] == NULL){
        return FALSE;
    }
    return TRUE;
}
bool checkReservationAlreadyExist(BranchManager ** branchManager, int branchId, int areaId){
    int i = 0;
    while(TRUE){
        Reservation * reservation = (*branchManager) -> reservation[i];
        if(reservation == NULL)
            break;
        if(reservation->studyAreaBranchId == branchId && 
            reservation -> studyAreaId == areaId &&
             isAfter(reservation->startAt, reservation->startHour)){
            return TRUE;
            
        }
        ++i;
    }
    return FALSE;
}
bool checkReservationAlreadyExistInBranch(BranchManager ** branchManager, int id){
    int i = 0;
    while(TRUE){
        Reservation * reservation = (*branchManager) -> reservation[i];
        if(reservation == NULL)
            break;
        if(reservation->studyAreaBranchId == id && isAfter(reservation->startAt, reservation->startHour)){
            return TRUE;
            
        }
        ++i;
    }
    return FALSE;
}

void branchCreate(BranchManager ** branchManager, int id){
    int i;
    if(checkStudyAreaBranchExist(branchManager, id)){
        printf("이미 해당 지점이 존재합니다.\n");
        return;
    }
    (*branchManager) ->studyAreaBranch[id] = (StudyAreaBranch * )malloc(sizeof(StudyAreaBranch));

    for(i = 0; i < STUDY_AREA_SIZE; ++i){
        (*branchManager) ->studyAreaBranch[id]->studyArea[i] = NULL;
    }
    saveBranches(branchManager);
}

void branchDelete(BranchManager ** branchManager, int id){
    int i = 0;

    if(!checkStudyAreaBranchExist(branchManager, id)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return;
    }

    if(checkReservationAlreadyExistInBranch(branchManager, id)){
            printf("해당 스터디 지점에 유효한 예약이 존재합니다.\n");
            return;
    }

    for(i = 0; i < STUDY_AREA_SIZE; ++i){
        free((*branchManager) -> studyAreaBranch[ id ] -> studyArea[i]);
        (*branchManager) -> studyAreaBranch[ id ] -> studyArea[i] = NULL;
    }

    free((*branchManager) ->studyAreaBranch [ id ]);
    (*branchManager) ->studyAreaBranch [ id ] = NULL;

    saveBranches(branchManager);
}

bool branchModify(BranchManager ** branchManager, int id){
    if(!checkStudyAreaBranchExist(branchManager, id)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return FALSE;
    }

    if(checkReservationAlreadyExistInBranch(branchManager, id)){
            printf("해당 스터디 지점에 유효한 예약이 존재합니다.\n");
            return FALSE;
    }

    return TRUE;
}

void manageStudyArea(BranchManager ** branchManager, int branchId){
    int action;
    action = inputUInt("작업 선택: 1.스터디 공간 추가 2.스터디 공간 삭제 3.스터디 공간 수정 4. 돌아가기 : ", 1, 4);
      
    switch (action){
        int studyAreaId;
        int capacity;
        case 1: {
            studyAreaId  = inputUInt("추가할 스터디 공간의 번호를 입력하세요 (1~5) : ", 1, 5);
            capacity  = inputUInt("스터디 공간의 허용 인원을 입력해 주세요 (1~10) : ", 1, 10);
            addStudyArea(branchManager, branchId, studyAreaId - 1, capacity);
            break;
        } case 2: {
            studyAreaId  = inputUInt("삭제할 스터디 공간의 번호를 입력하세요 (1~5) : ", 1, 5);
            deleteStudyArea(branchManager, branchId, studyAreaId - 1);
            break;
        } case 3: {
            studyAreaId  = inputUInt("수정할 스터디 공간의 번호를 입력하세요 (1~5) : ", 1, 5);
            capacity  = inputUInt("수정할 스터디 공간의 허용 인원을 입력해주세요 (1~10) : ", 1, 10);
            updateStudyAreaCapacity(branchManager, branchId , studyAreaId - 1, capacity);
            break;
        } case 4:
            return;
    }
}

void addStudyArea(BranchManager ** branchManager, int branchId, int studyAreaId, int capacity){
    printf("1234");
    if(!checkStudyAreaBranchExist(branchManager, branchId)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return;
    }
    if( (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] != NULL){
        printf("이미 스터디 공간이 존재합니다.\n");
        return;
    }
    (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] = (StudyArea *)malloc(sizeof(StudyArea));
    (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] -> id = studyAreaId;
    (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] -> capacity = capacity;
    printf("%d", (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] -> id);
    saveBranches(branchManager);
}

void deleteStudyArea(BranchManager ** branchManager, int branchId, int studyAreaId){
    if(!checkStudyAreaBranchExist(branchManager, branchId)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return;
    }
    if((*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] == NULL){
        printf("해당 스터디 공간이 존재하지 않습니다.\n");
        return;
    }

    if(checkReservationAlreadyExist(branchManager, branchId, studyAreaId)){
        printf("해당 스터디 지점에 유효한 예약이 존재합니다.\n");
        return;
    }

    free ((*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId]);
    (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] = NULL;

    saveBranches(branchManager);

}

void updateStudyAreaCapacity(BranchManager ** branchManager, int branchId, int studyAreaId , int capacity){
    if(!checkStudyAreaBranchExist(branchManager, branchId)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return;
    }
    if((*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] == NULL){
        printf("해당 스터디 공간이 존재하지 않습니다.\n");
        return;
    }
    (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] -> capacity = capacity;
    saveBranches(branchManager);
}

void doAdminAction(BranchManager ** branchManager) {
    int action;
    action = inputUInt("작업 선택: 1.지점 추가 2.지점 삭제 3.지점 수정 4.돌아가기: ", 1, 4);
      
    switch (action){
        int branchId;
        case 1: {
            branchId = inputUInt("추가할 지점의 번호를 입력하세요 (1~6) : ", 1, 6);
            branchCreate(branchManager, branchId - 1);
            break;
        } case 2: {
            branchId = inputUInt("삭제할 지점의 번호를 입력하세요 (1~6) : ", 1, 6);
            branchDelete(branchManager, branchId - 1);
            break;
        } case 3: {
            branchId = inputUInt("수정할 지점의 번호를 입력하세요 (1~6) : ", 1, 6);
            if(branchModify(branchManager, branchId - 1));
                manageStudyArea(branchManager, branchId - 1);
            break;
        } case 4:
            return;
    }
}

void setCurrentCustomer(BranchManager ** branchManager, char * id){
    strcpy((*branchManager) ->currentCustomerId, id);
}

void doUserAction(BranchManager ** branchManager) {
    char * id;
    int action;

    id = inputChar("사용자 ID를 입력하세요 (영문자, 숫자 조합으로 5~10 글자) : ", "^[a-zA-Z0-9]*$", 5, 10);
    setCurrentCustomer(branchManager, id);

    action = inputUInt("작업 선택: 1.스터디공간 조회 2.스터디공간 예약 3.예약 조회 4.예약 수정 5.예약 삭제 6.돌아가기 : ", 1, 6);
    
    switch (action){
        case 1: {
            printf("스터디 지점을 조회합니다.\n");
            lookUpBranches(branchManager);
            break;
        } case 2: {
            printf("스터디 공간 예약을 진행합니다.\n");
            createReservation(branchManager);
            break;
        } case 3: {
            printf("나의 예약을 조회합니다.(현재 시간 이후)\n");
            lookUpMyReservations(branchManager);
            break;
        } case 4: {
            printf("나의 예약을 수정합니다. (사용 인원 외 변경사항은 예약 삭제후 재등록 해주세요)\n");
            modifyReservation(branchManager);
            break;
        } case 5: {
            printf("나의 예약을 삭제합니다.\n");
            deleteReservation(branchManager);
            break;
        } case 6:
            return;
    }
}

void lookUpBranches(BranchManager ** branchManager){
    int branchId;
    printBranchesTable(branchManager);
    branchId = inputUInt("조회할 지점 번호를 입력하세요 (1~6) : ", 1, 6);
    printStudyAreaTable(branchManager, branchId - 1);
}

void printBranchesTable(BranchManager ** branchManager){
        int i;
        bool isAllEmpty = TRUE;
        printf("============지점목록============\n");
        for(i = 0; i < STUDY_AREA_BRANCH_SIZE; ++i){
            if( (*branchManager) -> studyAreaBranch[i] != NULL){
                printf("%d 번 지점\n", i+1);
                isAllEmpty = FALSE;
            }
        }
        if(isAllEmpty) 
            printf("지점이 하나도 존재하지 않습니다\n");
        printf("============================\n");
}

void printStudyAreaTable(BranchManager ** branchManager, int branchId){
        bool isAllEmpty = TRUE;
        int i;
        printf("============스터디 공간 목록============\n");
        if((*branchManager) -> studyAreaBranch[branchId] == NULL){ 
            printf("해당 지점은 존재하지 않습니다.\n");
            return;
        }
        for(i = 0; i < STUDY_AREA_SIZE; ++i) {
            if((*branchManager) -> studyAreaBranch[branchId] ->studyArea[i] != NULL){
                printf(" %d 번 스터디 공간 - 사용 가능 인원 : %d\n", i + 1,
                 (*branchManager) -> studyAreaBranch[branchId] ->studyArea[i] -> capacity);
                isAllEmpty = FALSE;
            }
        }
        if(isAllEmpty) 
            printf("스터디 공간이 존재하지 않습니다.\n");
        printf("===================================\n");
}

bool checkNewReservationOverlap(BranchManager ** branchManager, char * startAt, int startAtTime, int hours, int branchId, int areaId){
    
    int i = 0;
    while(TRUE){
        if( (*branchManager) -> reservation[i] == NULL)
            break;
        if( (*branchManager) -> reservation[i] -> studyAreaId == areaId &&
        (*branchManager) -> reservation[i] -> studyAreaBranchId == branchId){
            
            char startTimeBuffer[20];
            strcpy(startTimeBuffer, startAt);
            char startHourBuffer[3];
            itoa(startAtTime, startHourBuffer);
            if(startAtTime < 10){
                startHourBuffer[1] = startHourBuffer[0];
                startHourBuffer[0] = '0';
                startHourBuffer[2] = '\0';
            }
            int startTime = atoi(strcat(startTimeBuffer, startHourBuffer));

            itoa((*branchManager) -> reservation[i] -> startHour, startHourBuffer);
            char dest[20];
            strcpy(dest, (*branchManager) -> reservation[i] -> startAt);
            int reservationTime = atoi( strcat(dest, startHourBuffer));

            if(!( reservationTime >= startTime + hours || startTime <= reservationTime + (*branchManager) -> reservation[i] -> hours)){
                printf("해당 스터디 지점에 유효한 예약이 존재합니다.\n");
                return FALSE;
            }
        }
        ++i;
    }
    return TRUE;
}
void branchCreateReservation(BranchManager ** branchManager, int branchId, int studyAreaId, char * startAtStr, int startAtTime, int duringHours, int customerCnt){
    int i = 0;
    if(!checkStudyAreaBranchExist(branchManager, branchId)){
        printf("해당 지점이 존재하지 않습니다.\n");
        return;
    }
    if((*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] == NULL){
        printf("해당 스터디 공간이 존재하지 않습니다.\n");
        return;
    }
    if(customerCnt > (*branchManager) -> studyAreaBranch[branchId] -> studyArea[studyAreaId] -> capacity){
        printf("해당 스터디 공간의 가용인원을 초과하였습니다\n");
        return;
    }
    if(!checkNewReservationOverlap(branchManager, startAtStr, startAtTime, duringHours, branchId, studyAreaId)){
        return;
    }
    
    while(TRUE){
        if((*branchManager) -> reservation[i] == NULL)
            break;
        ++i;
    }
    (*branchManager) -> reservation[i] = (Reservation *)malloc(sizeof(Reservation));
    (*branchManager) -> reservation[i] -> customerId = (char *)malloc(strlen((*branchManager) -> currentCustomerId) +1);
    strcpy((*branchManager) -> reservation[i] -> customerId, (*branchManager) -> currentCustomerId);
    (*branchManager) -> reservation[i] -> studyAreaBranchId = branchId; 
    (*branchManager) -> reservation[i] -> studyAreaId = studyAreaId; 
    (*branchManager) -> reservation[i] -> customerCnt = customerCnt;
    (*branchManager) -> reservation[i] -> startAt = startAtStr;
    (*branchManager) -> reservation[i] -> startHour = startAtTime;
    (*branchManager) -> reservation[i] -> hours = duringHours;
    (*branchManager) -> reservation[i] -> hashcode = time(NULL);
    saveBranches(branchManager);
    saveReservations(branchManager);
}
void createReservation(BranchManager ** branchManager){
    int branchId;
    int studyAreaId;
    char * startAtStr;
    char dest[10] = "20";
    int startAtTime;
    int duringHours;
    int customerCnt;

    branchId = inputUInt("예약할 지점의 번호를 입력하세요 (1~6) : ", 1, 6);
    studyAreaId = inputUInt("예약할 스터디 공간을 입력하세요 (1~5) : ", 1, 5);
    startAtStr = strcat(dest, inputChar("예약 일자를 입력하세요 (YYMMDD) : ", "", 6, 6));
    startAtTime = inputUInt("시작 시간을 입력하세요 (8 ~ 21) : ", 8, 21);
    duringHours = inputUInt("사용 시간을 입력하세요(이용할 시간 숫자로) : ", 1, 22 - startAtTime);
    customerCnt = inputUInt("사용 인원을 입력해주세요 : ", 1, 10);

    branchCreateReservation(branchManager, branchId - 1, studyAreaId -1, startAtStr, startAtTime, duringHours, customerCnt);
}

void lookUpMyReservations(BranchManager ** branchManager){
    int i = 0;
    bool isAllEmpty = TRUE;


    printf("============지점목록============\n");
    while(TRUE){
        if( (*branchManager) -> reservation[i] == NULL)
            break;
        if( strcmp((*branchManager) -> reservation[i] -> customerId , (*branchManager) -> currentCustomerId) == 0 ){
            if(isAfter((*branchManager) -> reservation[i] ->startAt, (*branchManager) -> reservation[i] ->startHour)){
                printf("예약고객 아이디: %s, 지점 번호 : %d, 스터디공간 번호: %d, 사용 인원: %d, 예약 시간: %s %d, 사용 기간: %d 시간, 예약 코드 : %ld\n\n", 
                (*branchManager) -> reservation[i] -> customerId, 
                (*branchManager) -> reservation[i] -> studyAreaBranchId + 1, 
                (*branchManager) -> reservation[i] -> studyAreaId + 1, 
                (*branchManager) -> reservation[i] -> customerCnt, 
                (*branchManager) -> reservation[i] -> startAt, 
                (*branchManager) -> reservation[i] -> startHour,
                (*branchManager) -> reservation[i] -> hours,
                (*branchManager) -> reservation[i] -> hashcode);
                isAllEmpty = FALSE;
            }
        }
        ++i;
    }
    if(isAllEmpty == TRUE)
        printf("예약 내역이 존재하지 않습니다.\n");
    printf("=============================\n");
}

void modifyMyReservation(BranchManager ** branchManager, long reservationCode, int customerCnt){
    int i = 0;
    while(TRUE){
        if( (*branchManager) -> reservation[i] == NULL)
            break;
        if((*branchManager) ->reservation[i] ->hashcode == reservationCode){
            if(strcmp((*branchManager) -> reservation[i] ->customerId, (*branchManager) ->currentCustomerId) == 0 ){
                int capacity = (*branchManager) ->studyAreaBranch[(*branchManager) ->reservation[i] ->studyAreaBranchId] ->studyArea[ (*branchManager) ->reservation[i]->studyAreaId]->capacity;

                if(customerCnt <= capacity){
                    (*branchManager) -> reservation[i] ->customerCnt = customerCnt;
                    saveBranches(branchManager);
                    saveReservations(branchManager);
                    return;
                }
                printf("해당 스터디 공간의 가용인원을 초과하였습니다.\n");
                return;
            }
            printf("현재 로그인한 회원의 예약이 아닙니다.\n");
            return;
        }
        ++i;
    }
    printf("코드에 해당하는 예약이 존재하지 않습니다.\n");
    return;
}

void modifyReservation(BranchManager ** branchManager){
    long reservationCode;
     int customerCnt;
    reservationCode = inputLong("수정할 예약의 예약코드를 입력해주세요 : ", 1, __LONG_MAX__);
    customerCnt = inputUInt("수정할 예약 인원을 입력해주세요 (1~10) : ", 1, 10);

    modifyMyReservation(branchManager, reservationCode, customerCnt);
}

void deleteReservation(BranchManager ** branchManager){
    long reservationCode;
    reservationCode = inputLong("삭제할 예약의 예약코드를 입력해주세요 : ", 1, __LONG_MAX__);
    deleteMyReservation(branchManager, reservationCode);
}

void deleteMyReservation(BranchManager ** branchManager, long hashcode){
    int i = 0;
    Reservation * reservation = NULL;
    
    while(TRUE){
        if( (*branchManager) -> reservation[i] == NULL)
            break;
        if((*branchManager) ->reservation[i] ->hashcode == hashcode){
            if(strcmp((*branchManager) -> reservation[i] ->customerId, (*branchManager) ->currentCustomerId) == 0 ){
                    int j = i;
                    while( TRUE ){
                        if( (*branchManager) -> reservation[j] == NULL)
                        break;
                        ++j;
                    }
                    // free((*branchManager) -> reservation[i] -> customerId);
                    // free((*branchManager) -> reservation[i] -> startAt);

                    if(abs(i-j) > 1){
                        memcpy((*branchManager) -> reservation[i], (*branchManager) -> reservation[j-1], sizeof(Reservation));
                        // free((*branchManager) -> reservation[j-1] -> customerId);
                        // free((*branchManager) -> reservation[j-1] -> startAt);
                        // free((*branchManager) -> reservation[j-1] );
                        (*branchManager) -> reservation[j-1] = NULL;
                    }else{
                        (*branchManager) -> reservation[i] = NULL;
                    }

                    saveBranches(branchManager);
                    saveReservations(branchManager);
                    return;
            }
            printf("현재 로그인한 회원의 예약이 아닙니다.\n");
            return;
        }
        ++i;
    }
    printf("코드에 해당하는 예약이 존재하지 않습니다.\n");
}
