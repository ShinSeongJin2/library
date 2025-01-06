<template>
    <v-card outlined>
        <v-card-title>
            CreateLoan
        </v-card-title>

        <v-card-text>
            <String label="MemberId" v-model="value.memberId" :editMode="editMode"/>
            <Date label="LoanDate" v-model="value.loanDate" :editMode="editMode"/>
            <Date label="ReturnDueDate" v-model="value.returnDueDate" :editMode="editMode"/>
        </v-card-text>

        <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn
                    color="primary"
                    text
                    @click="createLoan"
            >
                CreateLoan
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
        name: 'CreateLoanCommand',
        components:{},
        props: {},
        data: () => ({
            editMode: true,
            value: {},
        }),
        created() {
            this.value.memberId = '';
            this.value.loanDate = '2025-01-06';
            this.value.returnDueDate = '2025-01-06';
        },
        watch: {
        },
        methods: {
            createLoan() {
                this.$emit('createLoan', this.value);
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

