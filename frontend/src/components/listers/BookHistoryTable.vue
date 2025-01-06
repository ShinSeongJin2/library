<template>

    <v-data-table
            :headers="headers"
            :items="bookHistory"
            :items-per-page="5"
            class="elevation-1"
    ></v-data-table>

</template>

<script>

    export default {
        name: 'BookHistoryList',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { 
                    text: "historyId", 
                    value: "historyId" 
                },
                { 
                    text: "actionDate", 
                    value: "actionDate" 
                },
                { 
                    text: "actionType", 
                    value: "actionType" 
                },
                { 
                    text: "bookStatus", 
                    value: "bookStatus" 
                },
            ],
            bookHistory : [],
        }),
        async created() {
            if(!this.value) this.value = [];
        },
        methods: {
        }
    }
</script>

