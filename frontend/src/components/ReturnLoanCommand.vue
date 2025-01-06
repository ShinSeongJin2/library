<template>
    <v-card outlined>
        <v-card-title>
            ReturnLoan
        </v-card-title>

        <v-card-text>
            <Date label="ReturnDate" v-model="value.returnDate" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="returnLoan"
            >
                ReturnLoan
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
        name: 'ReturnLoanCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.returnDate = '2025-01-06';
        },
        watch: {
        },
        methods: {
            returnLoan() {
                this.$emit('returnLoan', this.value);
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

