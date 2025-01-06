<template>
    <v-card outlined>
        <v-card-title>
            CreateReservation
        </v-card-title>

        <v-card-text>
            <String label="MemberId" v-model="value.memberId" :editMode="editMode"/>
            <Date label="ReservationDate" v-model="value.reservationDate" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="createReservation"
            >
                CreateReservation
            </v-btn>
            
            <v-btn
                    color="primary"
                    text
                    @click="close"
            >
                Close
            </v-btn>
        </v-card-actions>
    </v-card>

</template>

<script>
   
    export default {
        name: 'CreateReservationCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.memberId = '';
            this.value.reservationDate = '2025-01-06';
        },
        watch: {
        },
        methods: {
            createReservation() {
                this.$emit('createReservation', this.value);
            },
            close() {
                this.$emit('closeDialog');
            },
            change() {
                this.$emit('input', this.value);
            },
        }
    }
</script>

