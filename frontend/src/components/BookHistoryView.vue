<template>

    <v-data-table
        :headers="headers"
        :items="bookHistory"
        :items-per-page="5"
        class="elevation-1"
    ></v-data-table>

</template>

<script>
    const axios = require('axios').default;

    export default {
        name: 'BookHistoryView',
        props: {
            value: Object,
            editMode: Boolean,
            isNew: Boolean
        },
        data: () => ({
            headers: [
                { text: "id", value: "id" },
            ],
            bookHistory : [],
        }),
          async created() {
            var temp = await axios.get(axios.fixUrl('/bookHistories'))

            temp.data._embedded.bookHistories.map(obj => obj.id=obj._links.self.href.split("/")[obj._links.self.href.split("/").length - 1])

            this.bookHistory = temp.data._embedded.bookHistories;
        },
        methods: {
        }
    }
</script>

